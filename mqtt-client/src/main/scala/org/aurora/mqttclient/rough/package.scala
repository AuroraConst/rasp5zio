package org.aurora.mqttclient.rough

import scala.collection.mutable

//Messages that have Json Codecs
trait Msg

type MsgHandler[M <: Msg] = M => Unit



// each topic can have multiple handlers 
trait MsgDispatcher[M <: Msg] :
  val dispatchTopicMsgHandler : mutable.Map[TopicSubscriberDispatcher[M], mutable.Seq[MsgHandler[M]]] = mutable.Map.empty

  def subscribe (topic:TopicSubscriberDispatcher[M],h:MsgHandler[M]):Unit = ???
    // dispatchTopicMsgHandler(topic) :+ h
    // ()

  
  def dispatch (topic:TopicSubscriberDispatcher[M], msgJson:String) : Unit = 
    val optM = topic.msg(msgJson)
    optM.foreach{ _m =>
      this.dispatch(topic,_m)
    }

  def dispatch (topic:TopicSubscriberDispatcher[M], msg: M) : Unit  =
    dispatchTopicMsgHandler(topic).foreach { _(msg) }



sealed trait TopicSubscriberDispatcher[M <: Msg] extends MsgDispatcher[M]:
  val name: String
  def msg(jsonString:String): Option[M]

  def dispatch(msgJson:String):Unit = 
    dispatch(this,msgJson)







trait TopicMsgCodec:
  val topic:String

  


