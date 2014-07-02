package co.s4n

import javax.mail.{Message, Session => MailSession, Transport}
import javax.mail.internet.MimeMessage
import scala.concurrent.{ExecutionContext, Future}

object Mailer {
  def apply( host: String, port: Int ): Session.Builder =
    Mailer( ).session.host( host ).port( port )
}

case class Mailer( _session: MailSession = Defaults.session ) {

  def session = Session.Builder( this )

  def apply( e: Envelope )( implicit ec: ExecutionContext ): Future[ Unit ] = {
    val msg = new MimeMessage( _session ) {
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

    Future {
      Transport.send( msg )
    }
  }
}
