package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._


import zio.*
import zio.test.* 
import org.eclipse.paho.client.mqttv3.MqttClient
import zio.stream.ZStream
import java.io.IOException

// TODO finish this test to demonstrate how to use ZIO's acquire-release pattern for managing resources like MQTT clients.

object ZioBasicAcquireRelease extends ZIOSpecDefault:
  // 1. Define the Service Interface
  def spec = suite("ZioBasicTest")(
    test("ZIO effect") {
      val effect = ZIO.succeed(42)
      assertZIO(effect)(Assertion.equalTo(42))
    },
    test("MyApp runs") {
      import zio.test.Live

      for{
        _ <- Live.live(program) // Run the console input loop in a live environment
      } yield assertTrue(true) // Placeholder assertion, replace with actual checks as needed

    }   
    
  )

  def use(resource: MqttClient): Task[Any] = ZIO.attempt(???)
  def release(resource: MqttClient): UIO[Unit] = ZIO.succeed(MqttSubscriber.unsubscribe())
  def acquire: Task[MqttClient]                = 
    Console.printLine("Acquiring MQTT Client resource...") *>
    ZIO.attempt{
      MqttSubscriber.subscribedClient
    }

  // The recursive loop that takes console input
  val loopConsoleInput: ZIO[Any, IOException, Unit] = for {
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




