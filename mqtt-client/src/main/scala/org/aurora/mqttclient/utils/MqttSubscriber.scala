package org.aurora.mqttclient.utils
import org.eclipse.paho.client.mqttv3.*
import org.aurora.mqttclient.devices.Registry
import org.aurora.mqttclient.utils.mqqtAddress
object MqttSubscriber:

  lazy val clientId = "Arnold"
  lazy val topic : String = "zigbee2mqtt/Plug Bathroom Heater"
  lazy val topic1 : String = "zigbee2mqtt/Plug Master Bedroom"
  lazy val topic2 : String = "Master Bedroom Heaters/set"
  lazy val client = new MqttClient(mqqtAddress,clientId)

  private var _messageHandler: Option[(String, MqttMessage) => Unit] = None
  def messageHandler = _messageHandler
  def messageHandler_=(msg: (String, MqttMessage) => Unit): Unit = 
    _messageHandler = Some(msg)
    

  //subscribed according to Registry, which is populated by devices on startup. This way we can have a single client that subscribes to all topics for all devices, and we can manage the subscriptions in one place (the Registry) rather than having each device manage its own MQTT client and subscriptions.
  lazy val subscribedClient = 
    client.setCallback(new MqttCallback {
      override def connectionLost(cause: Throwable): Unit = println("Connection lost: " + cause.getMessage)
      override def messageArrived(topic: String, message: MqttMessage): Unit = 
        // println(s"Message arrived on topic $topic: ${message.toString}")
        messageHandler.foreach(handler => handler(topic, message))
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = println("Delivery complete")
    })

    client.connect()
    
    client

  def unsubscribe() = client.unsubscribe(topic)

