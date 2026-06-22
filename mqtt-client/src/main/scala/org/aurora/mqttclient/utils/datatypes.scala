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

