package co.s4n

import javax.mail.event.{TransportListener, TransportEvent}

case class YuubinTransportListener(
  delivered: TransportEvent => Unit,
  notDelivered: TransportEvent => Unit,
  partiallyDelivered: TransportEvent => Unit
) extends TransportListener {

  override def messageDelivered( e: TransportEvent ): Unit = delivered( e )

  override def messageNotDelivered( e: TransportEvent ): Unit = notDelivered( e )

  override def messagePartiallyDelivered( e: TransportEvent ): Unit = partiallyDelivered( e )
}