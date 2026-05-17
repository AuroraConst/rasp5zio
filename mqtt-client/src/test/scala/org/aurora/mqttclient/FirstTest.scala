package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._

class FirstTest extends AnyWordSpec with should.Matchers:

  "Publishing to Scene" should {
    "work like this" in {
      import org.aurora.mqttclient.datatypes.*
      MqttSubscriber.subscribe()
      MasterBedroomHeatersSet.SceneType.Half ().publish()
      MasterBedroomLightsSet.SceneType.Off().publish() 
      GarageBikeCharger.SceneType.Off().publish()
      // Thread.sleep(2000)
    }
  }
