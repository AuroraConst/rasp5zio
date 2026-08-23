package org.aurora.api
import zio._
import zio.http._


import zio.json._

import zio.http.template._
import org.aurora.dto.Hello
import zio.http.Body.ContentType
import zio.http.Header.{CacheControl, Expires}
/**
 * Important to understand the zio Handler type. In order to understand more information on zio handlers:
  https://ziohttp.com/reference/handler/

  Handler has four type parameters. The first two parameters R and Err are the environment and error type of the underlying effect that the handler represents. The third and fourth parameters In and Out are the input and output types of the handler.
  If the input type of the handler is Request and the output type is Response, we call that handler a request handler:
    type RequestHandler[-R, +Err] = Handler[R, Err, Request, Response]

 Type Parameters Overview:
 R (Environment): Contravariant (-) requirement, meaning the handler needs services or a context (like a database connection) before it can run.
 Err (Error): Covariant (+) type of failure that can occur during execution.
 In (Input): Contravariant (-) data type that the handler accepts as input (such as an HTTP Request).
 Out (Success): Covariant (+) data type that the handler produces upon success (such as an HTTP Response)

*/
object MyPi5Routes: 

  val app = Routes(
    Method.GET / "" -> handler {Response.redirect(URL(Path.root / "docs" / "index.html"))},//(Path.root / "docs" /"index.html")) },
    Method.GET / "docs" ->  handler {Response.redirect(URL(Path.root / "docs" / "index.html")) }, 
    Method.GET / "docs" / trailing ->   handler{  
      // val pathExtractor: Handler[Any, Nothing, (Path, Request), Path] = Handler.param[(Path, Request)](_._1)  //returns the path
      for{
          path <- Handler.param[(Path, Request)](_._1)//pathExtractor
          r    <- handler{ZIO.logInfo(s"Request for path: $path").as(Response.text(s"Request for path: $path"))}
          // response  <- fileutils.staticFileHandler(path).contramap[(Path, Request)](_._2)         
        } yield r //response
    },
    Method.GET / "mqttapp" -> handler{ Response.redirect(URL(Path.root / "docs"/ "mqttapp" / "index.html")) },
    Method.GET / "log" /trailing-> handler{ 
      for {
        path <- Handler.param[(Path, Request)](_._1)
        x <- handler{ZIO.logInfo("Hello from log handler").as(Response.text("Logged a message! $path"))}
      } yield x
    },
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") },
    Method.GET / "pwd"  -> handler{ Response.text(s"Current working directory: ${os.pwd}") }
  ).sandbox

