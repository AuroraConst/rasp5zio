package org.aurora.mqttclient
import zio.json.*

object codec:
  sealed trait  MessageTopicName [T]:
    val topic: String // = this.getClass().getSimpleName().stripSuffix("$")
    type  handlerType =  T => Unit
    var defaultHandler:Option[handlerType] = None
    def defaultHandler_=(dh:handlerType): Unit =
      defaultHandler = Some(dh)


  sealed trait TopicMsgCodec  extends MessageTopicName[TopicMsgCodec]

  object TopicMsgCodec :
    given JsonCodec[TopicMsgCodec] = DeriveJsonCodec.gen[TopicMsgCodec]

  case class  MessageString(s:String) extends TopicMsgCodec :
    override val topic: String = "topicMessageString"
  object MessageString :
    given JsonCodec[MessageString] = DeriveJsonCodec.gen[MessageString]


  case class MessageMyData(str: String, i:Int) extends TopicMsgCodec :
    override val topic: String = "topicMyData"

  object MessageMyData :
    given JsonCodec[MessageMyData] = DeriveJsonCodec.gen[MessageMyData]


