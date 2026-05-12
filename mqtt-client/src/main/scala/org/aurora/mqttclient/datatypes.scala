package org.aurora.mqttclient.datatypes
import zio.json._
import org.aurora.mqttclient.Publisher



trait ScenePayloadId  :
  val id: Int
  val topic: SceneTopic
  def jsonPayload = SceneRecallPayload(id).toJson
  def payload:Array[Byte] = jsonPayload.getBytes()
  def publish() =  Publisher.publish(topic, this)

trait SceneTopic(topic: String) :
  val topicString:String = topic

object MasterBedroomLightsSet extends SceneTopic("zigbee2mqtt/Master Bedroom Lights/set") :
  enum  SceneType(_id: Int) extends ScenePayloadId :
    override val id = _id
    val topic = MasterBedroomLightsSet
    case On() extends SceneType(0)
    case Off() extends SceneType(2)
    


object MasterBedroomHeatersSet extends SceneTopic("zigbee2mqtt/Master Bedroom Heaters/set") :
  enum SceneType(_id:Int) extends ScenePayloadId :
    override val id = _id
    val topic = MasterBedroomHeatersSet
    case On() extends SceneType(20)
    case Off() extends SceneType(21)   
    case Half() extends SceneType(22)


case class SceneRecall( scene_recall: ScenePayloadId):
  val value = SceneRecallPayload(scene_recall.id)
  

case class SceneRecallPayload(scene_recall: Int)
object SceneRecallPayload {
  given JsonCodec[SceneRecallPayload] = DeriveJsonCodec.gen[SceneRecallPayload]  
}

