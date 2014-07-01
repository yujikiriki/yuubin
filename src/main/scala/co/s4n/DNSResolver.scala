package co.s4n

object DNSResolver {
  import javax.mail.URLName
  import scala.annotation.tailrec
  import org.xbill.DNS._

  def mxRecordsFrom( hostname: String ): Map[ Int, URLName ] = {
    val records: Array[ Record ] = findMXRecords( hostname )
    val recordsUrl: Array[ (Int, URLName) ] = defineMXRecordURL( records )
    recordsUrl.toMap
  }

  private def defineMXRecordURL( records: Array[ Record ] ): Array[ (Int, URLName) ] = {
    val recordsUrl = toMxRecord( records ).map {
      mxRecord =>
        val targetString: String = mxRecord.getTarget( ).toString( )
        val urlName: String = targetString.substring( 0, targetString.length - 1 )
        ( mxRecord.getPriority -> new URLName( s"smtp://${urlName}" ) )
    }
    recordsUrl
  }

  private def findMXRecords( hostname: String ): Array[ Record ] = {
    val lookup = Option( new Lookup( hostname, Type.MX ).run( ) )
    val records: Array[ Record ] = lookup match {
      case Some( records ) =>
        records
      case None =>
        subdomainLookup( hostname ).getOrElse( Array( ) )
    }
    records
  }

  private def toMxRecord( records: Array[ Record ] ): Array[ MXRecord ] =
    records.map( record => record.asInstanceOf[ MXRecord ] )

  private def subdomainLookup( hostname: String ): Option[ Array[ Record ] ] = {
    @tailrec
    def rec0( hostname: String, records: Option[ Array[ Record ] ] ): Option[ Array[ Record ] ] = {
      if ( hostname.indexOf( "." ) != hostname.lastIndexOf( "." ) && hostname.lastIndexOf( "." ) != -1 ) {
        records match {
          case Some( r ) =>
            val upperHostname: String = hostname.substring( hostname.indexOf( "." ) + 1 )
            rec0( upperHostname, Option( new Lookup( hostname, Type.MX ).run( ) ) )
          case None =>
            records
        }
      }
      else
        records
    }

    rec0( hostname.substring( hostname.indexOf( "." ) + 1 ), Some( Array( ) ) )
  }
}
