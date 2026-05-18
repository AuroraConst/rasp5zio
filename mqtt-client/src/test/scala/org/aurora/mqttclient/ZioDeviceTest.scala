package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._


import zio.*
import zio.test.* 
import org.eclipse.paho.client.mqttv3.MqttClient
import zio.stream.ZStream
import java.io.IOException
import org.aurora.mqttclient.devices.SonoffPlug
import org.aurora.mqttclient.devices.ThirdReality

// TODO finish this test to demonstrate how to use ZIO's acquire-release pattern for managing resources like MQTT clients.

object ZioDeviceTest extends ZIOSpecDefault:
  import org.aurora.mqttclient.datatypes.*
  import org.aurora.mqttclient.devices.{Registry, ThirdRealityPlug}
  Registry.addDevices(
    Seq(
      SonoffPlug("Plug Bathroom Heater"),
      ThirdRealityPlug("Plug Master Bedroom" ),
      ThirdRealityPlug("Plug Garage")
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
              case SonoffPlug(name) =>
                val parsedMessage = device.message(message.toString)
                println(parsedMessage)
              case ThirdRealityPlug(name) =>
                val parsedMessage = device.message(message.toString)
                val casted  = parsedMessage.asInstanceOf[ThirdReality.RootInterface]
                println(s"Garage power: ${casted.power}")
                println(s"state: ${casted.state}")
                // println(parsedMessage)
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




