package co.s4n

import javax.mail.{Session => MailSession, SendFailedException, Address, Transport, Message, MessagingException}
import javax.mail.internet.MimeMessage
import com.sun.mail.smtp.{SMTPSendFailedException, SMTPTransport}
import scala.concurrent.{Future, ExecutionContext}
import scala.util.Try

class Yuubin( listener: YuubinTransportListener ) {
  import Yuubin._

  def asyncSend( e: Envelope )( implicit ec: ExecutionContext ): Future[ List[ YuubinSendResult ] ] = {
    val s: MailSession = YuubinSessionBuilder.build( e )
    val m = buildMessage( e, s )
    Future {
      sendToRecipient( e, m, s ).toList
    }
  }

  def send( e: Envelope ): List[ YuubinSendResult ] = {
    val s: MailSession = YuubinSessionBuilder.build( e )
    sendToRecipient( e, buildMessage( e, s ), s ).toList
  }

  private[ Yuubin ] def sendToRecipient( e: Envelope, m: MimeMessage, s: MailSession ): Seq[ YuubinSendResult ] =
    e.to.flatMap {
      address =>
        DNSResolver.mxRecordFrom( chopHostNameFrom( address.getAddress ) ) map {
          host =>
            connectAndSend( m, new SMTPTransport( s, host ), Array( address ) )
        }
    }

  private[ Yuubin ] def connectAndSend( mimeMessage: MimeMessage, transport: Transport, recipient: Array[ Address ] ): YuubinSendResult = {
    transport.addTransportListener( listener )

    val res: Try[ Unit ] = for {
      connected <- Try( transport.connect( ) )
      messageSent <- Try( transport.sendMessage( mimeMessage, recipient ) )
    } yield messageSent

    res.map {
      _ =>
        val smtpTransport = transport.asInstanceOf[ SMTPTransport ]
        transport.close()
        MessageSent( smtpTransport.getLastServerResponse )
    }
    .recover {
      case ssfe: SMTPSendFailedException => // If the send failed because of an SMTP command error
        transport.close( )
        SMTPCommandError( ssfe )
      case sfe: SendFailedException => // If the send failed because of invalid addresses.
        transport.close( )
        InvalidAddresses( sfe )
      case me: MessagingException => // If the connection is dead or not in the connected state or if the message is not a MimeMessage.
        transport.close( )
        me.getMessage( ).charAt( 0 ) match {
          case '5' => SMTPPermanentFailure( me )
          case '4' => SMTPPersistentTemporaryFailure( me )
          case _ => UnexpectedResponse( me )
        }
    }.get
  }
}

object Yuubin {

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

  private[ Yuubin ] def chopHostNameFrom( emailAddress: String ): String =
    emailAddress.substring( emailAddress.lastIndexOf( "@" ) + 1 )

}

sealed trait YuubinSendResult
case class MessageSent( serverResponse: String ) extends YuubinSendResult
case class SMTPCommandError( e: Throwable ) extends YuubinSendResult
case class InvalidAddresses( e: Throwable ) extends YuubinSendResult
case class SMTPPermanentFailure( e: Throwable ) extends YuubinSendResult
case class SMTPPersistentTemporaryFailure( e: Throwable ) extends YuubinSendResult
case class UnexpectedResponse( e: Throwable ) extends YuubinSendResult
