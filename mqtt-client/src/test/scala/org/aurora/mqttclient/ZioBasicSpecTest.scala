package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._


import zio.*
import zio.test.* 

/**
  * Remember there cannot be shared states in ZIO fibers, so we cannot use the same stateful objects across tests. Each test should create its own instance of any stateful objects it needs. This is because ZIO tests run in parallel by default, 
  * and sharing mutable state can lead to unpredictable behavior.
  */

object ZioBasicTest extends ZIOSpecDefault:
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




