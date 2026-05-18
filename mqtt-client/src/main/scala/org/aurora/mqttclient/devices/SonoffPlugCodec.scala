package org.aurora.mqttclient.devices

import zio.json.*
case class SonoffPlugCodec(name:String) extends DeviceMessage[SonoffPlugCodec.RootInterface] :
  override def message(message: String): SonoffPlugCodec.RootInterface =
    message.fromJson[SonoffPlugCodec.RootInterface] match
    case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
    case Right(parsedMessage) => parsedMessage.asInstanceOf[SonoffPlugCodec.RootInterface]
   

object SonoffPlugCodec:
  case class RootInterface (
    state: String
  )
  object RootInterface :
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]
