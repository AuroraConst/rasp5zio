package org.aurora.mqttclient

import org.aurora.mqttclient.devices.ThirdRealityPlugCodec

import zio.*
import org.eclipse.paho.client.mqttv3.MqttClient
import org.aurora.mqttclient.devices.{Registry, SonoffPlugCodec, TempHumid, LEDVanceCodec}
import zio.stream.ZStream
import java.io.IOException

object RoughMain extends ZIOAppDefault :

    
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

  def acquireReleaseProgram() = ZStream.acquireReleaseWith(acquire)(release)


  // The recursive loop that takes console input
  private val loopConsoleInput: ZIO[Any, IOException, Unit] = for {
    _     <- Console.printLine("Enter text (or type 'quit' to exit):")
    input <- Console.readLine
    _     <- ZIO.when(input != "quit")(loopConsoleInput) // Tail-recursive continuation
  } yield ()

   

  // val program :ZIO[Any, Throwable, Unit]  = 
  // for {
  //     _ <- Console.printLine("Starting MQTT Client...")
  //     r <- acquireReleaseProgram() 
  //         .flatMap { resource =>
  //           ZStream.fromZIO(loopConsoleInput)
  //         }
  //         .runDrain
      
  // } yield ()




  

  override def run: ZIO[ZIOAppArgs & Scope, Any, Any] = 

      for{
        _ <- Console.printLine("Starting MQTT Client...")
        _ <- Console.printLine(s"${ThirdRealityPlugCodec("Plug Garage").topic}")  
          // r <- acquireReleaseProgram() 
          // .flatMap { resource =>
          //   ZStream.fromZIO(loopConsoleInput)
          // }
          // .runDrain
      
        _ <- ZIO.attempt{
              // Publisher.publishFake(ThirdRealityPlugCodec("Plug Garage/set").topic)
              Publisher.publish(ThirdRealityPlugCodec("Plug Garage/set").topic, ThirdRealityPlugCodec.RootInterface().copy(countdown_to_turn_off  = 0))    
              // Publisher.disconnect()
          } 
      } yield()

        


