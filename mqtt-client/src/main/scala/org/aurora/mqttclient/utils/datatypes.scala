package org.aurora.mqttclient.datatypes
import zio.json._
// import org.aurora.mqttclient.utils.Publisher



case class SceneRecallPayload(scene_recall: Int)
object SceneRecallPayload :
  given JsonCodec[SceneRecallPayload] = DeriveJsonCodec.gen[SceneRecallPayload]  

trait Payload :
  def jsonPayload:String
  def payload:Array[Byte] = jsonPayload.getBytes()



trait ScenePayloadId extends Payload :
  val id: Int
  def jsonPayload = SceneRecallPayload(id).toJson

trait Topic:
  val topic: String


object MasterBedroomLightsSet extends Topic :
  override val topic: String = "zigbee2mqtt/Master Bedroom Lights/set"
  enum  SceneType(_id: Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(0)
    case Half() extends SceneType(3)
    case Dim() extends SceneType(4)
    case Off() extends SceneType(2)

object MasterBedroomHeatersSet extends Topic :
  override val topic = "zigbee2mqtt/Master Bedroom Heaters/set"
  enum SceneType(_id:Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(20)
    case Off() extends SceneType(21)   
    case Half() extends SceneType(22)

object GarageBikeCharger extends Topic :
  override val topic = "zigbee2mqtt/Garage bike charger/set"
  enum SceneType(_id:Int) extends ScenePayloadId :
    override val id = _id
    case On() extends SceneType(31)
    case Off() extends SceneType(35)