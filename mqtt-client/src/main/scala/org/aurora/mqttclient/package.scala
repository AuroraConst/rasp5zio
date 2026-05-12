package org.aurora.mqttclient

import java.util.UUID

import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient}
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.aurora.mqttclient.datatypes.SceneTopic
import org.aurora.mqttclient.datatypes.ScenePayloadId
import zio.json._
import org.aurora.mqttclient.datatypes.SceneRecallPayload



object Publisher:
  val publisherId = UUID.randomUUID().toString()
  val publisher = new MqttClient("tcp://192.168.0.199:1883", publisherId)
  val init: Unit =
    val options  = new MqttConnectOptions();

    options.setAutomaticReconnect(true);
    options.setCleanSession(true);
    options.setConnectionTimeout(10);
    publisher.connect(options)

  def publish(topic:SceneTopic, scenePayload: ScenePayloadId) =
    publisher.publish(topic.topicString,scenePayload.payload, 0, false) 




