package org.aurora.mqttclient
import org.eclipse.paho.client.mqttv3.*
object MqttSubscriber:

  lazy val clientId = "Arnold"
  lazy val topic : String = "zigbee2mqtt/Plug Bathroom Heater"
  lazy val topic1 : String = "zigbee2mqtt/Plug Master Bedroom"
  lazy val topic2 : String = "Master Bedroom Heaters/set"
  lazy val client = new MqttClient(mqqtAddress,clientId)

  def subscribedClient() = 
    client.setCallback(new MqttCallback {
      override def connectionLost(cause: Throwable): Unit = println("Connection lost: " + cause.getMessage)
      override def messageArrived(topic: String, message: MqttMessage): Unit = 
        println(s"Message arrived on topic $topic: ${message.toString}")
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = println("Delivery complete")
    })

    client.connect()
    client.subscribe(topic,1)
    client.subscribe(topic1,1)
    client.subscribe(topic2,1)
    client

  def unsubscribe() = client.unsubscribe(topic)

