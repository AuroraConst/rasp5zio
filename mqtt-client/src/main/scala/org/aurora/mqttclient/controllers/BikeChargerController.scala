package org.aurora.mqttclient.controllers


object BikeChargerController extends Publish :
  override val topic = "plug"
  
  enum Cmd extends CmdType :
    case PlugOn, PlugOff

  override def publish[T<:CmdType](cmd: T):Unit =
    import org.aurora.mqttclient.BikePlugControl
    cmd match
      case BikeChargerController.Cmd.PlugOn => BikePlugControl.turnOn
      case BikeChargerController.Cmd.PlugOff => BikePlugControl.turnOff

    

def main(args: Array[String]): Unit =
  BikeChargerController.publish(BikeChargerController.Cmd.PlugOff) // This works because Light is both a CmdType and a Publish
