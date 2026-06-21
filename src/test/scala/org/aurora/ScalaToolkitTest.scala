package org.aurora

import org.scalatest._
import wordspec._
import matchers._

class ScalaToolkitTest extends AnyWordSpec with should.Matchers:
  "os.Path" should {
    "work like this" in {
      val path = os.pwd / "target" / "docs" / "sites"
      info(s"Current path: ${path}")
    }
  }