package org.aurora.mqttclient.utils

import java.util.UUID

import org.eclipse.paho.client.mqttv3.{ MqttClient}
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.aurora.mqttclient.datatypes.{Payload}
import zio.json._
// import org.aurora.mqttclient.datatypes.SceneRecallPayload


val mqqtAddress = "tcp://mypi5:1883"


object Publisher:
  lazy val publisherId = UUID.randomUUID().toString() //required to ensure a clean session
  lazy val publisher = new MqttClient(mqqtAddress, publisherId)
  lazy val init: Unit =
    lazy val options  = new MqttConnectOptions();

    options.setAutomaticReconnect(true);
    options.setCleanSession(true);
    options.setConnectionTimeout(10);
    publisher.connect(options)

  def publish(topic:String, scenePayload: Payload) =
    init //ensure the publisher is connected before publishing
    publisher.publish(topic, scenePayload.payload, 0, false)
    disconnect()

  def disconnect() = publisher.disconnect()  



  def publishFake(topic:String) =
    init
    case class RootInterface(
      countdown_to_turn_on: Int,
      countdown_to_turn_off: Int = 18000,
      state: String  = "OFF"
    )
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]
    publisher.publish(topic, RootInterface(14400,18000,"OFF").toJson.getBytes(), 0, false)
    disconnect()





