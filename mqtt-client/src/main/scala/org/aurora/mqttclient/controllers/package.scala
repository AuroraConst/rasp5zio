package org.aurora.mqttclient.controllers




import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.utils.Publisher

trait ThirdRealityPlugControl :
  val device = ThirdRealityPlugCodec("Plug Garage/set")
  val maxDurationSeconds = 18000 // 5 hours in seconds

  def turnOnFor5hours: Unit = 
    Publisher.publish(device.topic, 
    ThirdRealityPlugCodec.Setters(
      countdown_to_turn_on = 0,
      countdown_to_turn_off = maxDurationSeconds,
      state = "ON"
    )
  )

  def turnOff: Unit = 
    Publisher.publish(device.topic, ThirdRealityPlugCodec.Setters(
      countdown_to_turn_on = 0,
      countdown_to_turn_off = 0,
      state = "OFF"
    ))

  

  def startCharging(hour:Int, minute:Int = 0)  =
    import java.time.{Duration, LocalDateTime, LocalTime, ZoneId}
    val zoneId: ZoneId = ZoneId.systemDefault()
    val now = LocalDateTime.now(zoneId)
    val targetTime = LocalTime.of(hour, minute)
  
    // Create today's target hour
    val nextHour = now.`with`(targetTime)
  
    // If it is already past hour AM today, target tomorrow's hour AM
    val nextHourAdjusted = if now.isAfter(nextHour) then nextHour.plusDays(1) else nextHour

    println(s"Next time: $nextHourAdjusted")
    println(s"Now: $now")
  
    // Calculate the duration in seconds
    val secondsFromNow = Duration.between(now, nextHourAdjusted).toSeconds.toInt
    println(s"Seconds until next hour: $secondsFromNow")

    Publisher.publish(device.topic, ThirdRealityPlugCodec.Setters(
      countdown_to_turn_on = secondsFromNow,
      countdown_to_turn_off = maxDurationSeconds,
      state = "OFF"
    ))  




trait Publish :
  trait CmdType

  val topic:String
  def action[T<:CmdType](cmd: T):Unit = println(s"publishing on topic: $topic with cmd: $cmd")


object Light extends Publish :
  override val topic = "light"
  
  enum LightCmds extends CmdType :
    case LightOn, LightOff

  override def action[T<:CmdType](cmd: T):Unit =
    println(s"publishing on topic: $topic with cmd: $cmd")


object Plug extends Publish :
  override val topic = "Plug Garage/set"
  
  enum PlugCmds extends CmdType :
    case PlugOn, PlugOff

  
  override def action[T<:CmdType](cmd: T):Unit =  ???
