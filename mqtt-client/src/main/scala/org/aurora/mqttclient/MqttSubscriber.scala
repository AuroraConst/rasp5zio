package org.aurora.mqttclient
import org.eclipse.paho.client.mqttv3.*
object MqttSubscriber:
  val broker = mqqtAddress

  val clientId = "Arnold"
  val topic : String = "zigbee2mqtt/Plug Master Bedroom Heater"
  val client = new MqttClient(mqqtAddress,clientId)

  def subscribe() = 
    client.setCallback(new MqttCallback {
      override def connectionLost(cause: Throwable): Unit = println("Connection lost: " + cause.getMessage)
      override def messageArrived(topic: String, message: MqttMessage): Unit = 
        println(s"Message arrived on topic $topic: ${message.toString}")
        println("Unsubscribing...")
        unsubscribe()
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = println("Delivery complete")
    })

    client.connect()
    client.subscribe(topic,1)

  def unsubscribe() = client.unsubscribe(topic)