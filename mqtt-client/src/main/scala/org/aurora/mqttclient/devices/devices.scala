package org.aurora.mqttclient
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttMessage}
import scala.collection.mutable


package devices:

  trait MsgDispatcher:
    //device name -> handler function
    val map: mutable.Map[String, Msg => Unit] = mutable.Map.empty
    def dispatchMessage(message: Msg): Unit



  trait Msg :
    type  handlerType =  Msg => Unit
    var defaultHandler:Option[handlerType] = None
    def defaultHandler_=(dh:handlerType): Unit =
      defaultHandler = Some(dh)


  //TODO try making T <: CommonTrait and then having the message method return T instead of Any. 
  //This way we can have a common interface for all devices, but still have type safety for the specific device types.
  
  trait DeviceMessage[T <: Msg]:
    val name: String
    val topic: String = s"zigbee2mqtt/$name"
    def message(mm:MqttMessage): T = message(mm.toString)
    def message(jsonPayload: String): T 


    //TODO finish this method to dispatch the message to the appropriate handler based on the type of the device. This will likely involve pattern matching on the type of the device and then calling the appropriate handler function for that type.
    def dispatchMessage(message: T): Unit = ???

    def subscribe(): MqttClient = 
      import org.aurora.mqttclient.utils.MqttSubscriber
      val client = MqttSubscriber.subscribedClient
      client.subscribe(topic,1)
      client

