package org.aurora.mqttclient.devices

import scala.collection.mutable
object Registry :
  val devices: mutable.Map[String,DeviceMessage[?]] = mutable.Map.empty
  def addDevice(device: DeviceMessage[?]): Unit = 
    devices += (device.name -> device)
  def addDevices(newDevices: Seq[DeviceMessage[?]]): Unit = 
    newDevices.foreach(device => devices += (device.name -> device))

  def subscribe(): Unit = 
    devices.values.foreach(_.subscribe())  

  def device(topic: String): Option[DeviceMessage[?]] = 
    devices.values.find(_.topic == topic)
