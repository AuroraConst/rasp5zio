package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._


import zio.*
import zio.test.* 

// TODO finish this test to demonstrate how to use ZIO's acquire-release pattern for managing resources like MQTT clients.

object ZioBasicAcquireRelease extends ZIOSpecDefault:
  // 1. Define the Service Interface
  def spec = suite("ZioBasicTest")(
    test("ZIO effect") {
      val effect = ZIO.succeed(42)
      assertZIO(effect)(Assertion.equalTo(42))
    },
    test("MyApp runs") {
      assertZIO(program)(Assertion.equalTo(true))
    } 
    
  )

  
  val program  = 
    for {
        _ <- Console.printLine("Running MyApp...").orDie
        tf <- ZIO.succeed(true)
    } yield (tf)




