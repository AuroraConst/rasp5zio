package org.aurora.api
import zio._
import zio.http._
import zio.json._


import org.aurora.dto.Hello



object HelloRoutes: 
  val app = Routes(
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") }
  )
 
  