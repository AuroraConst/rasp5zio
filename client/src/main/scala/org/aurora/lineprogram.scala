
package org.aurora
import zio._

object lineprogram :
  lazy val program = for {
    _ <- zio.Console.printLine("-" * 80)
  } yield ()
