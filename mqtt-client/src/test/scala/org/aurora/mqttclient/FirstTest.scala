package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.devices.ThirdRealityPlug
import org.aurora.mqttclient.devices.Registry

class FirstTest extends AnyWordSpec with should.Matchers:
  "parsing" should {
    "work like this" in {
      val json = """{"ac_frequency":60,"countdown_to_turn_off":15014,"countdown_to_turn_on":0,"current":0.39,"energy":1.01,"linkquality":93,"power":10.8,"power_factor":0,"power_on_behavior":null,"reset_total_energy":null,"state":"ON","update":{"installed_version":268513381,"latest_release_notes":null,"latest_source":"https://raw.githubusercontent.com/Koenkk/zigbee-OTA/master/images/ThirdReality/SmartPlug_Zigbee_PROD_OTA_V101_1.01.01.ota","latest_version":268513381,"state":"idle"},"voltage":121.8}"""

      import zio.json.*
      import org.aurora.mqttclient.devices.*
      val parsed = json.fromJson[ThirdReality.RootInterface]
      parsed match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => 
          result.ac_frequency shouldEqual 60
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
