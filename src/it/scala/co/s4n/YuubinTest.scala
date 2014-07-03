package co.s4n

import javax.mail.event.TransportEvent
import javax.mail.internet.InternetAddress
import org.scalatest.FunSuite

class YuubinTest extends FunSuite {

  test( "Simple send test" ) {
    import scala.concurrent.ExecutionContext
    import scala.concurrent.Await
    import scala.concurrent.duration._

    implicit val ec: ExecutionContext = ExecutionContext.global

    val e = Envelope
      .from( new InternetAddress( "yujikiriki@s4n.co" ) )
      .to( new InternetAddress( "aaa@s4n.com" ), new InternetAddress( "ykr@s4n.com" ) )
      .cc( new InternetAddress( "" ) )
      .subject( "I bug you" )
      .content( Text( "Bug." ) )

    val delivered: TransportEvent => Unit = {
      event =>
        println( "*****************************" )
        println( s"Delivered" )
        println( s"Invalid addresses: ${event.getInvalidAddresses}" )
        println( s"Message: ${event.getMessage}" )
        println( s"Type: ${event.getType}" )
        println( s"Valid sent addresses: ${event.getValidSentAddresses}" )
        println( s"Valid unsent addresses: ${event.getValidUnsentAddresses}" )
    }

    val notDelivered: TransportEvent => Unit = {
      event =>
        println( "*****************************" )
        println( s"Not delivered" )
        println( s"Invalid addresses: ${event.getInvalidAddresses}" )
        println( s"Message: ${event.getMessage}" )
        println( s"Type: ${event.getType}" )
        println( s"Valid sent addresses: ${event.getValidSentAddresses}" )
        println( s"Valid unsent addresses: ${event.getValidUnsentAddresses}" )
    }

    val partiallyDelivered: TransportEvent => Unit = {
      event =>
        println( "*****************************" )
        println( s"Partially delivered" )
        println( s"Invalid addresses: ${event.getInvalidAddresses}" )
        println( s"Message: ${event.getMessage}" )
        println( s"Type: ${event.getType}" )
        println( s"Valid sent addresses: ${event.getValidSentAddresses}" )
        println( s"Valid unsent addresses: ${event.getValidUnsentAddresses}" )
    }

    val y = new Yuubin( new YuubinTransportListener( delivered, notDelivered, partiallyDelivered ) )
    val result = y.asyncSend( e )
    val list: Seq[ YuubinSendResult ] = Await.result( result, 10.seconds )
    list.foreach( r => println( s"Last response: ${r}" ) )
  }

}
