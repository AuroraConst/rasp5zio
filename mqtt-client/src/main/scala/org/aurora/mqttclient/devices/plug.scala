package org.aurora.mqttclient.devices
import zio.json._
import org.eclipse.paho.client.mqttv3.MqttClient

trait DeviceMessage[T]:
  val name: String
  val topic: String = s"zigbee2mqtt/$name"
  def message(message: String): T 
  def subscribe(): MqttClient = 
    import org.aurora.mqttclient.MqttSubscriber
    val client = MqttSubscriber.subscribedClient
    client.subscribe(topic,1)
    client


case class ThirdRealityPlug(name:String) extends DeviceMessage[ThirdReality.RootInterface]:
  override def message(message: String): ThirdReality.RootInterface = 
    message.fromJson[ThirdReality.RootInterface] match
      case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
      case Right(parsedMessage) => parsedMessage

case class SonoffPlug(name:String) extends DeviceMessage[SonoffPlug.RootInterface]:
  override def message(message: String): SonoffPlug.RootInterface = 
    message.fromJson[SonoffPlug.RootInterface] match
      case Left(error) => throw new RuntimeException(s"Failed to parse JSON: $error")
      case Right(parsedMessage) => parsedMessage      

object SonoffPlug:
  case class RootInterface (
    state: String
  )
  object RootInterface :
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]


       


object ThirdReality:
  case class RootInterface (
    ac_frequency: Int,
    countdown_to_turn_off: Int,
    countdown_to_turn_on: Int,
    current: Double,
    energy: Double,
    linkquality: Int,
    power: Double,
    power_factor: Int,
    power_on_behavior: Option[String],
    reset_total_energy: Option[String],
    state: String,
    update: Update,
    voltage: Double
  )

  object RootInterface :
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
