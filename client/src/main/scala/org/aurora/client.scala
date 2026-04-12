package org.aurora

import zio._
import sttp.client4.*
import sttp.client4.httpclient.zio.*  
import sttp.client4.httpclient.zio.HttpClientZioBackend
import sttp.model.Uri
import zio.json._
import configuration.{appConfigLayer,AppConfig}
import org.aurora.dto.JSCodeced

package object client :
  val baseUri = uri"http://localhost:8080"
  case class Response(status: String, message: String, result: String) :
    lazy val eth = result.toDouble / 1E18
  object Response {
    given JsonDecoder[Response] = DeriveJsonDecoder.gen[Response]
  }

  import org.aurora.dto.Hello
  def get():  ZIO[AppConfig & Scope, Throwable|String, Unit] =
    for {
      appConfig <- ZIO.service[AppConfig]
      backend   <- HttpClientZioBackend.scoped()
      response  <- basicRequest
        .get(uri"${baseUri}")
        .send(backend)
      body      <- ZIO.fromEither(response.body)
      r <- ZIO.fromEither(body.fromJson[Hello])
      _         <- Console.printLine(s"Response: $r")
    } yield ()

  def get(uri: Uri):  ZIO[AppConfig & Scope, Throwable|String, String] =
    for {
      appConfig <- ZIO.service[AppConfig]
      backend   <- HttpClientZioBackend.scoped()
      response  <- basicRequest
        .get(uri)
        .send(backend)
      body      <- ZIO.fromEither(response.body)
      
    } yield {body}


  // def workflow =   
  //   get()

  val program1 = 
    get(baseUri).provide(
      appConfigLayer,
      Scope.default
    )
 

  val program  = 
    
    get().provide(
      appConfigLayer,
      Scope.default
    )

