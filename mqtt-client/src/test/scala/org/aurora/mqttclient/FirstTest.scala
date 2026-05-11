package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._

class FirstTest extends AnyWordSpec with should.Matchers:
  "this" should {
    "work" in {

      true should be(true)
    }
  }

  "Publishing to Scene" should {
    "work like this" in {
      import org.aurora.mqttclient.datatypes.*
      val sceneRecall = SceneRecall(SceneType.Off()).value
      val m = SceneMsg[Payload](Scene("zigbee2mqtt/Master Bedroom Lights/set"),sceneRecall)
      publisher.publish(m.scene.topic, m.jsonPayload, 0, false) 
    }
  }
