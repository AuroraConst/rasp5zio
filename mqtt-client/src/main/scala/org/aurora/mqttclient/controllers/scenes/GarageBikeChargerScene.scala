package org.aurora.mqttclient.scenes
import org.aurora.mqttclient.datatypes.*


object GarageBikeChargerScene extends Topic :
  override val topic = "zigbee2mqtt/Garage bike charger/set"
  enum SceneType(_id:Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(31)
    case Off() extends SceneType(35)