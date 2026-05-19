package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._


import zio.*
import zio.test.* 
import org.eclipse.paho.client.mqttv3.MqttClient
import zio.stream.ZStream
import java.io.IOException
import org.aurora.mqttclient.devices.{SonoffPlugCodec,ThirdRealityPlugCodec,LEDVanceCodec,TempHumid}
import java.time.temporal.Temporal


object ZioDeviceTest extends ZIOSpecDefault:
  import org.aurora.mqttclient.datatypes.*
  import org.aurora.mqttclient.devices.{Registry, ThirdRealityPlugCodec}
  Registry.addDevices(
    Seq(
      SonoffPlugCodec("Plug Bathroom Heater"),
      ThirdRealityPlugCodec("Plug Master Bedroom" ),
      ThirdRealityPlugCodec("Plug Garage"),
      LEDVanceCodec("Light Bulb1"),
      LEDVanceCodec("Light Bulb2"),
      TempHumid("Temp/Humidity Kitchen"),
      TempHumid("Temp/Humidity Aerogarden")
    )
  )


  // 1. Define the Service Interface
  def spec = suite("ZioDeviceTest")(

    test("Device subscribe runs") {
      import zio.test.Live
      
      for{
        _ <- Live.live(program) // Run the console input loop in a live environment
      } yield assertTrue(true) // Placeholder assertion, replace with actual checks as needed

    }   
    
  )

  def release(resource: MqttClient): UIO[Unit] = ZIO.succeed(MqttSubscriber.unsubscribe())
  def acquire: Task[MqttClient]                = 
    Console.printLine("Acquiring MQTT Client resource...") *>
    ZIO.attempt{
      //creates mqqt client and subscribes to topics according to Registry, which is populated by devices on startup. This way we can have a single client that subscribes to all topics for all devices, and we can manage the subscriptions in one place (the Registry) rather than having each device manage its own MQTT client and subscriptions.
      val client = MqttSubscriber.subscribedClient
      MqttSubscriber.messageHandler = 
        (topic, message) => {
          Registry.device(topic).foreach { device =>
            device match {
              case SonoffPlugCodec(name) =>
                val parsedMessage = device.message(message.toString)
                println(parsedMessage)
              case ThirdRealityPlugCodec(name) =>
                val parsedMessage = device.message(message.toString)
                val casted  = parsedMessage.asInstanceOf[ThirdRealityPlugCodec.RootInterface]
                println(s"Garage power: ${casted.power}")
                println(s"state: ${casted.state}")
              case LEDVanceCodec(name) =>
                val parsedMessage = device.message(message.toString)  
                println(parsedMessage)
              case TempHumid(name) => 
                val parsedMessage = device.message(message.toString)
                val casted = parsedMessage.asInstanceOf[TempHumid.RootInterface]
                println(s"Battery: ${casted.battery}")
                println(s"Humidity: ${casted.humidity}")
                println(s"Temperature: ${casted.temperature}")
            }
          }
        }    

      println("Connected to MQTT broker at " + mqqtAddress)
      Registry.subscribe()
      client
    }

  // The recursive loop that takes console input
  private val loopConsoleInput: ZIO[Any, IOException, Unit] = for {
    _     <- Console.printLine("Enter text (or type 'quit' to exit):")
    input <- Console.readLine
    _     <- ZIO.when(input != "quit")(loopConsoleInput) // Tail-recursive continuation
  } yield ()

  

  def acquireReleaseProgram() = ZStream.acquireReleaseWith(acquire)(release)
  
  val program :ZIO[Any, Throwable, Unit]  = 
    for {
        _ <- Console.printLine("Starting MQTT Client...")
        r <- acquireReleaseProgram() 
            .flatMap { resource =>
              ZStream.fromZIO(loopConsoleInput)
            }
            .runDrain
        
    } yield ()




