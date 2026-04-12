package org.aurora.dto

sealed trait JSCodeced

case class Hello(message: String) extends JSCodeced

object Hello:
  import zio.json._
  given JsonCodec[Hello] = DeriveJsonCodec.gen[Hello]