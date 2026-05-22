package org.aurora.mqttclient

import java.util.UUID

import org.eclipse.paho.client.mqttv3.{IMqttClient, MqttClient}
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.aurora.mqttclient.datatypes.SceneTopic
import org.aurora.mqttclient.datatypes.ScenePayloadId
import zio.json._
import org.aurora.mqttclient.datatypes.SceneRecallPayload


val mqqtAddress = "tcp://mypi5.local:1883"


object Publisher:
  lazy val publisherId = UUID.randomUUID().toString() //required to ensure a clean session
  lazy val publisher = new MqttClient(mqqtAddress, publisherId)
  lazy val init: Unit =
    lazy val options  = new MqttConnectOptions();

    options.setAutomaticReconnect(true);
    options.setCleanSession(true);
    options.setConnectionTimeout(10);
    publisher.connect(options)

  def publish(topic:SceneTopic, scenePayload: ScenePayloadId) =
    init //ensure the publisher is connected before publishing
    publisher.publish(topic.topicString,scenePayload.payload, 0, false) 




