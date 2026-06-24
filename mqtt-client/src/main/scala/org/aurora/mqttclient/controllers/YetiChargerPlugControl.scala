package org.aurora.mqttclient.controllers

import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher


object YetiChargerPlugControl extends ThirdRealityPlugControl :
  override val device = ThirdRealityPlugCodec("Plug Yeti charger/set")
  override val maxDurationSeconds = 1500 // 25 minutes in seconds

   def turnOnFor25Minutes: Unit = 
    Publisher.publish(device.topic, 
    ThirdRealityPlugCodec.Setters(
      countdown_to_turn_on = 0,
      countdown_to_turn_off = maxDurationSeconds,
      state = "ON"
    )
  )


