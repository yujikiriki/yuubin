package co.s4n

import javax.mail.{Session => MailSession}
import java.util.Properties
import scala.concurrent.ExecutionContext

object Defaults {
  implicit val executionContext = ExecutionContext.Implicits.global

  val session = MailSession.getDefaultInstance(new Properties())
}
