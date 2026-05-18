package org.aurora.mqttclient.devices

import zio.json.*
case class TempHumid(name:String) extends DeviceMessage[TempHumid.RootInterface] :
  override def message(message: String): TempHumid.RootInterface =
    message.fromJson[TempHumid.RootInterface] match
    case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
    case Right(parsedMessage) => parsedMessage.asInstanceOf[TempHumid.RootInterface]
   

object TempHumid:
  case class RootInterface (
    battery: Int,
    humidity: Int,
    linkquality: Int,
    temperature: Double,
    update: Update
  )


  object RootInterface :
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]

  case class Update (
  installed_version: Int,
  latest_version: Int,
  state: Option[String]
)

  object Update :
    given JsonCodec[Update] = DeriveJsonCodec.gen[Update]

