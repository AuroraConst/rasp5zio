package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.devices.Registry

class CodecTest extends AnyWordSpec with should.Matchers:
  "parsing" should {
    "work like this" in {
      import zio.json.*
      import codec.*

      val json = """{"s":"hello"}"""

      MessageString("hello").toJson shouldEqual """{"s":"hello"}"""
      json.fromJson[MessageString] match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => result should be ( MessageString("hello"))
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
