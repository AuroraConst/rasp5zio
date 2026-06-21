package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec

import zio.json.*
import org.aurora.mqttclient.devices.*

class MqqtDeviceJsonTest extends AnyWordSpec with should.Matchers:
  s"${TempHumid.getClass.getSimpleName()}" should {
    "work like this" in {
      val json = """{"battery":100,"humidity":53,"linkquality":60,"temperature":18.7,"update":{"installed_version":-1,"latest_version":-1,"state":null}}"""
      val parsed = json.fromJson[TempHumid.RootInterface]
      parsed match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => 
          result.battery shouldEqual 100
      }
    }
  }

  s"${ThirdRealityPlugCodec.getClass().getSimpleName()}" should {
    "work like this" in {
      val json = """{"ac_frequency":60,"countdown_to_turn_off":28800,"countdown_to_turn_on":21600,"current":0,"energy":5.44,"linkquality":36,"power":0,"power_factor":0,"power_on_behavior":null,"reset_total_energy":null,"state":"OFF","update":{"installed_version":268513381,"latest_release_notes":null,"latest_source":"https://raw.githubusercontent.com/Koenkk/zigbee-OTA/master/images/ThirdReality/SmartPlug_Zigbee_PROD_OTA_V101_1.01.01.ota","latest_version":268513381,"state":"idle"},"voltage":122}'"""
      val parsed = json.fromJson[ThirdRealityPlugCodec.RootInterface]
      parsed match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => 
          result.copy(state = "ON").state shouldEqual "ON"
      }
    }
  }


  "" should {
    "work like this" in {
      val json1 = """{"ac_frequency":60,"countdown_to_turn_off":28800,"countdown_to_turn_on":21600,"current":0,"energy":5.44,"linkquality":84,"power":0,"power_factor":0,"power_on_behavior":null,"reset_total_energy":null,"state":"OFF","update":{"installed_version":268513381,"latest_release_notes":null,"latest_source":"https://raw.githubusercontent.com/Koenkk/zigbee-OTA/master/images/ThirdReality/SmartPlug_Zigbee_PROD_OTA_V101_1.01.01.ota","latest_version":268513381,"state":"idle"},"voltage":122.1}"""
      val json2 = """{"ac_frequency":60,"countdown_to_turn_off":28800,"countdown_to_turn_on":21600,"current":0,"energy":5.44,"linkquality":87,"power":0,"power_factor":0,"power_on_behavior":null,"reset_total_energy":null,"state":"OFF","update":{"installed_version":268513381,"latest_release_notes":null,"latest_source":"https://raw.githubusercontent.com/Koenkk/zigbee-OTA/master/images/ThirdReality/SmartPlug_Zigbee_PROD_OTA_V101_1.01.01.ota","latest_version":268513381,"state":"idle"},"voltage":122.1}"""

      val parsed1 = json1.fromJson[ThirdRealityPlugCodec.RootInterface].toOption.get
      val parsed2 = json2.fromJson[ThirdRealityPlugCodec.RootInterface].toOption.get
      
      parsed1.state shouldEqual "OFF" 
      parsed2.state shouldEqual "OFF"
    }
  }