package org.aurora.rough

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
  override val topic = "plug"
  
  enum PlugCmds extends CmdType :
    case PlugOn, PlugOff

  override def publish[T<:CmdType](cmd: T):Unit =
    println(s"publishing on topic: $topic with cmd: $cmd")

def main(args: Array[String]): Unit =
  Light.publish(Light.LightCmds.LightOn) // This works because Light is both a CmdType and a Publish
  Plug.publish(Plug.PlugCmds.PlugOn) // This works because Plug is both a CmdType and a Publish
