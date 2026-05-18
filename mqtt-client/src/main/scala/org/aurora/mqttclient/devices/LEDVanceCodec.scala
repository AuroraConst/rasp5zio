package org.aurora.mqttclient.devices

import zio.json.*
case class LEDVanceCodec(name:String) extends DeviceMessage[LEDVanceCodec.RootInterface] :
  override def message(message: String): LEDVanceCodec.RootInterface =
    message.fromJson[LEDVanceCodec.RootInterface] match
    case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
    case Right(parsedMessage) => parsedMessage.asInstanceOf[LEDVanceCodec.RootInterface]
   

object LEDVanceCodec:
  case class RootInterface (
    brightness: Int,
    effect: Option[String],
    linkquality: Int,
    state: String,
    update: Update
  )
  object RootInterface :
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]

  case class Update (
    installed_version: Int,
    latest_release_notes: Option[String],
    latest_source: Option[String],
    latest_version: Int,
    state: String
  )
  object Update :
    given JsonCodec[Update] = DeriveJsonCodec.gen[Update]

