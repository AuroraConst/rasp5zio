package org.aurora.mqttclient.devices

import zio.json._
import org.aurora.mqttclient.datatypes.Payload
case class ThirdRealityPlugCodec(name:String) extends DeviceMessage[ThirdRealityPlugCodec.RootInterface] :
  override def message(message: String): ThirdRealityPlugCodec.RootInterface =
    message.fromJson[ThirdRealityPlugCodec.RootInterface] match
      case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
      case Right(parsedMessage) => parsedMessage.asInstanceOf[ThirdRealityPlugCodec.RootInterface]
   


object ThirdRealityPlugCodec:
  case class  CountDownSetters(
    countdown_to_turn_off: Int,
    countdown_to_turn_on: Int
  )
  case class RootInterface (
    ac_frequency: Int,
    countdown_to_turn_off: Int,
    countdown_to_turn_on: Int,
    current: Double,
    energy: Double,
    linkquality: Int,
    power: Int,
    power_factor: Double,
    power_on_behavior: Option[String],
    reset_total_energy: Option[String],
    state: String,
    update: Update,
    voltage: Double
  ) extends Msg with Payload :
      def jsonPayload = this.toJson

  object RootInterface :
    def apply():RootInterface = RootInterface(0,0,0,0.0,0.0,0,0,0.0,None,None,"",Update(0, None, "", 0, ""),0)
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]  

  case class Update (
    installed_version: Int,
    latest_release_notes: Option[String],
    latest_source: String,
    latest_version: Int,
    state: String
  )
  object Update :
    given JsonCodec[Update] = DeriveJsonCodec.gen[Update]


