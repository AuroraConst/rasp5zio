package org.aurora.mqttclient.controllers

import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher


object HeaterBathroomPlugControl extends ThirdRealityPlugControl :
  override val device = ThirdRealityPlugCodec("Plug Bathroom Heater/set")


