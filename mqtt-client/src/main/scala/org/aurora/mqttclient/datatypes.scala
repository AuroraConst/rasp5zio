package org.aurora.mqttclient.datatypes
import zio.json._
import org.aurora.mqttclient.Publisher


trait Publishable[T]: //TODO finish this with a typeclass pattern for publishing varying T and converting to arraybyte payloads
  def topic: String
  // def publish() = Publisher.publish(TopicPayload(topic, jsonPayload))

  
trait ScenePayloadId :
  val id: Int

enum SceneTopic(topic: String) :
  val topicString:String = topic

  case MasterBedroomLightsSet() extends SceneTopic("zigbee2mqtt/Master Bedroom Lights/set")
    enum  SceneType(_id: Int) extends ScenePayloadId :
      val id = _id
      case On() extends SceneType(0)
      case Off() extends SceneType(2)
      def payload() = 
        SceneRecallPayload(this.id)


  case MasterBedroomHeatersSet() extends SceneTopic("zigbee2mqtt/Master Bedroom Heaters/set") 
    enum HeatingSceneId(_id:Int) extends ScenePayloadId :
      val id = _id
      case On() extends HeatingSceneId(20)
      case Off() extends HeatingSceneId(21)   
      case Half() extends HeatingSceneId(22)






case class SceneRecall( scene_recall: ScenePayloadId):
  val value = SceneRecallPayload(scene_recall.id)
  

case class SceneRecallPayload(scene_recall: Int)
object SceneRecallPayload {
  given JsonCodec[SceneRecallPayload] = DeriveJsonCodec.gen[SceneRecallPayload]  
}

