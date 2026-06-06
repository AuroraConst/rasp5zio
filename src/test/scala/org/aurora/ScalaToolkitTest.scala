package org.aurora

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.devices.ThirdRealityPlugCodec
import org.aurora.mqttclient.devices.Registry

class ScalaToolkitTest extends AnyWordSpec with should.Matchers:
  "os.Path" should {
    "work like this" in {
      val path = os.pwd / "target" / "docs" / "sites"
      info(s"Current path: ${path}")
    }
  }