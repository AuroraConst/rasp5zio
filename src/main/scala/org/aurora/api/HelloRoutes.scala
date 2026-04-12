package org.aurora.api
import zio._
import zio.http._
import zio.json._


import org.aurora.dto.Hello


object HelloRoutes: 
  val app: HttpApp[Any, Nothing] = Http.collectZIO {

  case Method.GET -> !!  =>
    ZIO.succeed {
      // Response.text("Hello, World!")
      Response.json(Hello("Hello, Arnold!!!!!").toJson)
    }
  }