package co.s4n

import javax.mail.internet.InternetAddress
import org.scalatest.FunSuite

class YuubinTest extends FunSuite {

  test( "Simple send test" ) {
    import scala.concurrent.ExecutionContext
    import scala.concurrent.Await
    import scala.concurrent.duration._

    val e = Envelope
    .from( new InternetAddress( "yujikiriki@s4n.co" ) )
    .to( new InternetAddress( "aaa@s4n.com" ), new InternetAddress( "angelarivers@gmail.com" ) )
    .cc( new InternetAddress( "angelarivers@gmail.com" ) )
    .subject( "I bug you" )
    .content( Text( "Bug." ) )


    implicit val _: ExecutionContext = ExecutionContext.global

    val y = Yuubin( )
    val result = y.send( e )
    val list: Seq[ String ] = Await.result( result, 10.seconds )
    list.foreach( println( _ ) )
  }

}
