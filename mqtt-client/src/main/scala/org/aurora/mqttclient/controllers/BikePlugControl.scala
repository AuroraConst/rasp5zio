package org.aurora.mqttclient.controllers


import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher




object BikePlugControl extends ThirdRealityPlugControl :
  override val device = ThirdRealityPlugCodec("Plug Garage/set")
  override val maxDurationSeconds = 18000 // 5 hours in seconds

