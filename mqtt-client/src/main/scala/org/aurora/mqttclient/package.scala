package org.aurora.mqttclient

import java.util.UUID

import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient}
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

val publisherId = UUID.randomUUID().toString()
val publisher = new MqttClient("tcp://192.168.0.199:1883", publisherId)
// val publisher = new MqttClient("tcp://iot.eclipse.org:1883", publisherId)



val options  = new MqttConnectOptions();

val init: Unit =
  options.setAutomaticReconnect(true);
  options.setCleanSession(true);
  options.setConnectionTimeout(10);
  publisher.connect(options)
  