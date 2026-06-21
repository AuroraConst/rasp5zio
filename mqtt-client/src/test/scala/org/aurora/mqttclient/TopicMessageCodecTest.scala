package org.aurora.mqttclient

import org.scalatest._
import wordspec._
import matchers._
import org.aurora.mqttclient.utils.codec

class CodecTest extends AnyWordSpec with should.Matchers:
  "parsing" should {
    "work like this" in {
      import zio.json.*
      import codec.*

      val json = """{"s":"hello"}"""

      MessageString("hello").toJson should be( """{"s":"hello"}""")

      json.fromJson[MessageString] shouldEqual Right(MessageString("hello"))
      json.fromJson[MessageString].toOption shouldEqual Some(MessageString("hello"))
      
      json.fromJson[MessageString] match {
        case Left(error) => fail(s"Failed to parse JSON: $error")
        case Right(result) => result should be ( MessageString("hello"))
      }
      
    }
  }
