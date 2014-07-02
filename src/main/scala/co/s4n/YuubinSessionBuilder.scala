package co.s4n

import java.util.Properties
import javax.mail.{Session => MailSession}

object YuubinSessionBuilder {

  def build( e: Envelope ): MailSession = {
    val mailSessionProps = new Properties( )
    mailSessionProps.put( "mail.mime.charset", "UTF-8" )
    mailSessionProps.put( "mail.smtp.connectiontimeout", "30000" )
    mailSessionProps.put( "mail.smtp.timeout", "30000" )
    MailSession.getInstance( mailSessionProps )
  }

}
