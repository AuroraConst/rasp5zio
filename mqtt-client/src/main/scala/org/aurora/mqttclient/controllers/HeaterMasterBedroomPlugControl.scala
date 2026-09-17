package org.aurora.mqttclient.controllers

import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher


object HeaterMasterBedroomPlugControl extends ThirdRealityPlugControl :
  override val device = ThirdRealityPlugCodec("Plug Master Bedroom/set")


