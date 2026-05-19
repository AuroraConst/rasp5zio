package org.aurora.mqttclient
import zio.json._
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage


package devices:
  //TODO try making T <: CommonTrait and then having the message method return T instead of Any. This way we can have a common interface for all devices, but still have type safety for the specific device types.
  trait DeviceMessage[T]:
    val name: String
    val topic: String = s"zigbee2mqtt/$name"
    def message(mm:MqttMessage): T = message(mm.toString)
    def message(message: String): T 


    //TODO finish this method to dispatch the message to the appropriate handler based on the type of the device. This will likely involve pattern matching on the type of the device and then calling the appropriate handler function for that type.
    def dispatchMessage(message: T): Unit = ???

    def subscribe(): MqttClient = 
      import org.aurora.mqttclient.MqttSubscriber
      val client = MqttSubscriber.subscribedClient
      client.subscribe(topic,1)
      client

