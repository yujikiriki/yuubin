package co.s4n

import javax.mail.event.TransportEvent
import org.scalatest.FunSuite

class YuubinMockTest extends FunSuite {

  test( "Simple send test using Mailbox" ) {
    import javax.mail.internet.InternetAddress
    import org.jvnet.mock_javamail.Mailbox

    val e = Envelope
      .from( new InternetAddress( "someone@gmail.com" ) )
      .to( new InternetAddress( "dev1@gmail.com" ) )
      .cc( new InternetAddress( "dev2@gmail.com" ) )
      .subject( "I bug you" )
      .content( Text( "Bug." ) )

    val delivered: TransportEvent => Unit = {
      event =>
        println( s"Delivered: ${event.toString}" )
    }

    val notDelivered: TransportEvent => Unit = {
      event =>
        println( s"Not delivered: ${event.toString}" )
    }

    val partiallyDelivered: TransportEvent => Unit = {
      event =>
        println( s"Partially delivered: ${event.toString}" )
    }

    val y = new Yuubin( new YuubinTransportListener( delivered, notDelivered, partiallyDelivered ) )
    val result: List[ YuubinSendResult ] = y.send( e )
    result.foreach( r => info( r.toString ) )

    val dev1Inbox = Mailbox.get( "dev1@gmail.com" )
    assert( dev1Inbox.size === 1 )
    val devMsg = dev1Inbox.get( 0 )
    info( s"Subject = ${devMsg.getSubject}" )
    assert( devMsg.getSubject === "I bug you" )
    info( s"Content= ${devMsg.getContent}" )
    assert( devMsg.getContent === "Bug." )
  }
}
