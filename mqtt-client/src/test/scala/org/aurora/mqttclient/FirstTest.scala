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
      import org.aurora.mqttclient.datatypes.SceneTopic.*
      import org.aurora.mqttclient.datatypes.*
      Publisher.publish(MasterBedroomLightsSet(), MasterBedroomLightsSet().SceneType.Off())
      Publisher.publish(MasterBedroomHeatersSet(),MasterBedroomHeatersSet().HeatingSceneId.On())
    }
  }
