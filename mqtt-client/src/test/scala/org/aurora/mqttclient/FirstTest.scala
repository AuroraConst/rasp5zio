package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.devices.Registry

class FirstTest extends AnyWordSpec with should.Matchers:
  "parsing" should {
    "work like this" in {
      val json = """{"battery":100,"humidity":53,"linkquality":60,"temperature":18.7,"update":{"installed_version":-1,"latest_version":-1,"state":null}}"""
      import zio.json.*
      import org.aurora.mqttclient.devices.*
      val parsed = json.fromJson[TempHumid.RootInterface]
      parsed match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => 
          result.battery shouldEqual 100
      }
    }
  }
  "Publishing to Scene" should {
    "work like this" in {



      // MqttSubscriber.subscribedClient()
      // MasterBedroomHeatersSet.SceneType.Off ().publish()
      // MasterBedroomLightsSet.SceneType.Off().publish() 
      // GarageBikeCharger.SceneType.Off().publish()
    }
  }
