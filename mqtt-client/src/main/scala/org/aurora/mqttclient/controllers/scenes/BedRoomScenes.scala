package org.aurora.mqttclient.scenes
import org.aurora.mqttclient.datatypes.*

object MasterBedroomLightsScene extends Topic :
  override val topic: String = "zigbee2mqtt/Master Bedroom Lights/set"
  enum  SceneType(_id: Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(0)
    case Half() extends SceneType(3)
    case Dim() extends SceneType(4)
    case Off() extends SceneType(2)

object MasterBedroomHeatersScene extends Topic :
  override val topic = "zigbee2mqtt/Master Bedroom Heaters/set"
  enum SceneType(_id:Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(20)
    case Off() extends SceneType(21)   
    case Half() extends SceneType(22)
 