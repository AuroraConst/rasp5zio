package org.aurora.mqttclient

import org.aurora.mqttclient.devices.{Registry, SonoffPlugCodec, TempHumid, LEDVanceCodec}
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec


def main(args: Array[String]): Unit =
  println(ThirdRealityPlugCodec("Plug Garage/sett").topic)
  Publisher.publishFake(ThirdRealityPlugCodec("Plug Garage/set").topic)
  // Publisher.publish(ThirdRealityPlugCodec("Plug Garage/set").topic, ThirdRealityPlugCodec.RootInterface().copy(countdown_to_turn_off = 0))
  Publisher.disconnect()