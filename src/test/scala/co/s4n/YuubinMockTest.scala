package co.s4n

import org.scalatest.FunSuite

class YuubinMockTest extends FunSuite {

  test( "Simple send test using Mailbox" ) {
    import scala.concurrent.ExecutionContext
    import javax.mail.internet.InternetAddress
    import org.jvnet.mock_javamail.Mailbox

    val e = Envelope
      .from( new InternetAddress( "someone@gmail.com" ) )
      .to( new InternetAddress( "dev1@gmail.com" ) )
      .cc( new InternetAddress( "dev2@gmail.com" ) )
      .subject( "I bug you" )
      .content( Text( "Bug." ) )

    implicit val _: ExecutionContext = ExecutionContext.global
    val y = Yuubin( )
    y.send( e )

    val dev1Inbox = Mailbox.get( "dev1@gmail.com" )
    assert( dev1Inbox.size === 1 )
    val devMsg = dev1Inbox.get( 0 )
    info( s"Subject = ${devMsg.getSubject}"  )
    assert( devMsg.getSubject === "I bug you" )
    info( s"Subject = ${devMsg.getContent}"  )
    assert( devMsg.getContent === "Bug." )
  }

}
