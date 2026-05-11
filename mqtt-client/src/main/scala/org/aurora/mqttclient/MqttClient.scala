package org.aurora.mqttclient

val a = 1

@main def run(): Unit =
  val r = init
  publisher.publish("test/topic", "Hello, MQTT!".getBytes(), 0, false)
  publisher.publish("test/topic", "Yes hello to you, MQTT!".getBytes(), 0, false)
  println("Hello, MQTT Client!")