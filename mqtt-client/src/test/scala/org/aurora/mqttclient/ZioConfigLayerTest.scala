package org.aurora.mqttclient

import zio.*
import zio.test.*
/**
  * Important Learning note:
    Instead of classical Singletons in Scala or Java, use ZIO Layers to manage shared resources and dependencies in a functional way. 
    This allows for better testability, modularity, and composability of your code. Each test can provide its own instance of the 
    service, ensuring isolation and preventing side effects between tests.
  */

trait Greeter :
  def sayHello(name: String): UIO[String]


object Greeter {
  val live: ZLayer[Any, Nothing, Greeter] = ZLayer.succeed(new Greeter {
    override def sayHello(name: String): UIO[String] = 
      ZIO.succeed(s"Hello, $name!")
  })

  //  Optional: Convenience accessor methods for easier usage
  def sayHello(name: String): URIO[Greeter, String] = 
    ZIO.serviceWithZIO[Greeter](_.sayHello(name))
}

object ZioConfigLayerTest extends ZIOSpecDefault:
  val program1: URIO[Greeter, String] = 
    for {
      result <- Greeter.sayHello("Program1")
    } yield result

  val program2: URIO[Greeter, String] = {
    for {
      result <- ZIO.serviceWithZIO[Greeter](_.sayHello("Program2"))
    } yield result
  }  

  def spec = suite("ZioConfigLayerTest")(
    test("Greeter service should say hello") {
      assertZIO(program1.provideLayer(Greeter.live))(Assertion.equalTo("Hello, Program1!"))
      assertZIO(program2.provideLayer(Greeter.live))(Assertion.equalTo("Hello, Program2!"))
    }
  )
