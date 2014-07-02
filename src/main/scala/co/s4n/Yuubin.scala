package co.s4n

import javax.mail.{Session => MailSession, Address, Transport, Message}
import javax.mail.internet.MimeMessage
import com.sun.mail.smtp.SMTPTransport
import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success, Try}

case class Yuubin( )( implicit val ec: ExecutionContext ) {

  import Yuubin._

  def send( e: Envelope ): Future[ Seq[ String ] ] = {
    val session: MailSession = YuubinSessionBuilder.build( e )
    val message = buildMessage( e, session )

    /* "To" recipients */
    val result = e.to.flatMap {
      address =>
        val toHostname = chopHostNameFrom( address.getAddress )
        DNSResolver.mxRecordFrom( toHostname ) map {
          host =>
            Future {
              connectAndSend(
                message,
                new SMTPTransport( session, host ),
                Array( address )
              )
            }
        }
    }
    Future.sequence( result )
  }
}

object Yuubin {

  import com.sun.mail.smtp.SMTPTransport

  private[ Yuubin ] def buildMessage( e: Envelope, session: MailSession ): MimeMessage =
    new MimeMessage( session ) {
      e.subject.map( setSubject( _ ) )
      setFrom( e.from )
      e.to.foreach( addRecipient( Message.RecipientType.TO, _ ) )
      e.cc.foreach( addRecipient( Message.RecipientType.CC, _ ) )
      e.bcc.foreach( addRecipient( Message.RecipientType.BCC, _ ) )
      e.headers.foreach( h => addHeader( h._1, h._2 ) )
      e.contents match {
        case Text( txt, charset ) => setText( txt, charset.displayName )
        case mp@Multipart( _ ) => setContent( mp.parts )
      }
    }

  def connectAndSend( mimeMessage: MimeMessage, transport: Transport, recipient: Array[ Address ] ): String = {

    val res: Try[ Boolean ] = for {
      connected <- Try( transport.connect( ) )
      messageSent <- Try( transport.sendMessage( mimeMessage, recipient ) )
      b <- Try( transport.isInstanceOf[ SMTPTransport ] )
    } yield b

    val lastResponse: String = res match {
      case Success( isSMTPTransport ) =>
        if( isSMTPTransport ) {
          val smtpTransport = transport.asInstanceOf[ SMTPTransport ]
          smtpTransport.getLastServerResponse( )
        }
        else
          "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
      case Failure( e ) =>
        println( "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" )
        e.getMessage
    }

    transport.close( )
    lastResponse
  }

  private[ Yuubin ] def chopHostNameFrom( emailAddress: String ): String =
    emailAddress.substring( emailAddress.lastIndexOf( "@" ) + 1 )
}
