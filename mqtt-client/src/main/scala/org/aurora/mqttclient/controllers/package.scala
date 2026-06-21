package org.aurora.mqttclient.controllers


trait Publish :
  trait CmdType

  val topic:String
  def publish[T<:CmdType](cmd: T):Unit = println(s"publishing on topic: $topic with cmd: $cmd")


object Light extends Publish :
  override val topic = "light"
  
  enum LightCmds extends CmdType :
    case LightOn, LightOff

  override def publish[T<:CmdType](cmd: T):Unit =
    println(s"publishing on topic: $topic with cmd: $cmd")


object Plug extends Publish :
  override val topic = "Plug Garage/set"
  
  enum PlugCmds extends CmdType :
    case PlugOn, PlugOff

  
  override def publish[T<:CmdType](cmd: T):Unit =  ???
