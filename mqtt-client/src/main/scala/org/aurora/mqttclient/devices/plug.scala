package org.aurora.mqttclient
import zio.json._
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage


package devices:
  trait DeviceMessage[T]:
    val name: String
    val topic: String = s"zigbee2mqtt/$name"
    def message(mm:MqttMessage): T = message(mm.toString)
    def message(message: String): T 

    def subscribe(): MqttClient = 
      import org.aurora.mqttclient.MqttSubscriber
      val client = MqttSubscriber.subscribedClient
      client.subscribe(topic,1)
      client

