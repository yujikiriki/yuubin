package co.s4n

object DNSResolver {

  import javax.mail.URLName
  import scala.annotation.tailrec
  import org.xbill.DNS._

  def mxRecordFrom( hostname: String ): Option[ URLName ] = {
    val recordsUrl: Array[ (Int, URLName) ] = defineMXRecordURL( queryMXRecords( hostname ) )
    val recordsUrlMap = recordsUrl.toMap
    if( recordsUrlMap.keys.isEmpty )
      None
    else
      recordsUrlMap.get( recordsUrlMap.keys.max )
  }

  private[ DNSResolver ] def queryMXRecords( hostname: String ): Array[ Record ] =
    Option( new Lookup( hostname, Type.MX ).run( ) ) match {
      case Some( records ) =>
        records
      case None =>
        subdomainLookup( hostname ).getOrElse( Array( ) )
    }

  private[ DNSResolver ] def defineMXRecordURL( records: Array[ Record ] ): Array[ (Int, URLName) ] =
    toMxRecord( records ).map {
      mxRecord =>
        val targetString: String = mxRecord.getTarget( ).toString( )
        val urlName: String = targetString.substring( 0, targetString.length - 1 )
        ( mxRecord.getPriority -> new URLName( s"smtp://${urlName}" ) )
    }

  private[ DNSResolver ] def toMxRecord( records: Array[ Record ] ): Array[ MXRecord ] =
    records.map( record => record.asInstanceOf[ MXRecord ] )

  private[ DNSResolver ] def subdomainLookup( hostname: String ): Option[ Array[ Record ] ] = {
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
