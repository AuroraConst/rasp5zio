package org.aurora.mqttclient

import org.aurora.mqttclient.devices.{Registry, SonoffPlugCodec, TempHumid, LEDVanceCodec}
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher


def main(args: Array[String]): Unit =
  BikePlugControl.turnOff
