package org.aurora.mqttclient.datatypes
import zio.json._

enum  SceneType(i:Int):
  val value = i
  case On() extends SceneType(0)
  case Off() extends SceneType(2)


object SceneType {
  given JsonCodec[SceneType] = DeriveJsonCodec.gen[SceneType]  
}  

case class SceneRecall( scene_recall: SceneType):
  val value = Payload(scene_recall.value)
  

case class Payload(scene_recall: Int)
object Payload {
  given JsonCodec[Payload] = DeriveJsonCodec.gen[Payload]  
}

case class Scene(topic:String)

object Scene {
  given JsonCodec[Scene] = DeriveJsonCodec.gen[Scene]  
}


case class SceneMsg[T](scene: Scene, payload: T):
  def jsonPayload(using JsonEncoder[T]) =
    payload.toJson.getBytes()
  

