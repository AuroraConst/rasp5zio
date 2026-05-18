package org.aurora.mqttclient.zio
import zio.json._


object ThirdReality:
  case class RootInterface (
    ac_frequency: Int,
    countdown_to_turn_off: Int,
    countdown_to_turn_on: Int,
    current: Int,
    energy: Double,
    linkquality: Int,
    power: Int,
    power_factor: Int,
    power_on_behavior: String,
    reset_total_energy: String,
    state: String,
    update: Update,
    voltage: Double
  )

  object RootInterface :
    given JsonCodec[RootInterface] = DeriveJsonCodec.gen[RootInterface]  

  case class Update (
    installed_version: Int,
    latest_release_notes: String,
    latest_source: String,
    latest_version: Int,
    state: String
  )
  object Update :
    given JsonCodec[Update] = DeriveJsonCodec.gen[Update]
