package org.aurora.api
import zio._
import zio.http._


import zio.json._

import zio.http.template._
import org.aurora.dto.Hello
import zio.http.Body.ContentType

import org.aurora.mqttclient.controllers.scenes.{MasterBedroomSceneController, GarageBikeChargerSceneController}
import org.aurora.mqttclient.controllers.BikePlugControl
/**
 * for more information on zio handlers:
  https://ziohttp.com/reference/handler/
*/
object MyPi5Routes: 
  import laika.api.Transformer
  import laika.format.{HTML, Markdown}
  import laika.config.SyntaxHighlighting

  val path: os.Path = os.pwd
  val indexHtmlPath = os.pwd / "target" / "docs" / "site" / "index.html"
  val docsBasePath = os.pwd / "target" / "docs" / "site"  

  def revisedPath(path:String): String =  
    for{
      _ <- ZIO.logInfo(s"Received path: $path")
    } yield ()
    if(path == "") s"$docsBasePath"
      else 
      {
        val revisedPath = docsBasePath / os.RelPath(path) 
        s"$revisedPath"
      }

  
  private def htmlResponse(html: String): Response =
    Response(body = Body.fromString(html))
      .addHeader(Header.ContentType(MediaType.text.html))

  private def docsHandler() = 
    handler{
      val extractPath    = Handler.param[(Path, Request)](_._1)
      val extractRequest = Handler.param[(Path, Request)](_._2)
      for{
         path <- extractPath 
         result <-  {
          val p = os.RelPath( s"$path")
          
          val basePathRevised =  if(p.toString == "") {docsBasePath}
            else docsBasePath / os.RelPath("/")
          val finalPath =   basePathRevised / p 

          Handler.fromFile(finalPath.toIO  )
          Handler.text(finalPath.toString)
         }
      } yield result.addHeader(Header.Vary(Header.Origin.name))
    }

  def readme(readmeContent: String) = 
    val transformer = Transformer
        .from(Markdown)
        .to(HTML)
        .using(Markdown.GitHubFlavor, SyntaxHighlighting)
        .build
    transformer.transform(readmeContent).toOption.getOrElse("Error transforming markdown")    
        

  val app = Routes(
    Method.GET / "" -> handler {Response.redirect(URL(Path.root / "docs" / "index.html"))},//(Path.root / "docs" /"index.html")) },
    Method.GET / "docs" ->  handler {Response.redirect(URL(Path.root / "docs" / "index.html")) }, 
    Method.GET / "docs" / trailing ->   handler{
      val pathExtractor: Handler[Any, Nothing, (Path, Request), Path] = //last types are input tuple to  output path
        Handler.param[(Path, Request)](_._1)  //returns the path

      // val requestExtractor: Handler[Any, Nothing, (Path, Request), Request] =  //request is not used
      //   Handler.param[(Path, Request)](_._2)

      def staticFileHandler(path: Path): Handler[Any, Throwable, Request, Response] = {
        val encodedPath = path.encode
        val basePathRevised = revisedPath(encodedPath)
        Handler.fromFile(os.Path(basePathRevised).toIO )
      }  
  
      for{
          path <- pathExtractor
          // request <- requestExtractor  //request is not used
          result  <- staticFileHandler(path).contramap[(Path, Request)](_._2)         
        } yield result
          
    },
    Method.GET / "log" -> handler{ 
      for {
        x <-ZIO.logInfo("Hello from log handler").as(Response.text("Logged a message!"))
      } yield x
    },
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") },
    Method.GET / "pwd"  -> handler{ Response.text(s"Current working directory: ${os.pwd}") },
    Method.GET / "masterlightson" -> handler{ MasterBedroomSceneController.on;Response.text("Master lights on!") },
    Method.GET / "masterlightsoff" -> handler{ MasterBedroomSceneController.off;Response.text("Master lights off!") },
    Method.GET / "pluggaragebikeon" -> handler{ GarageBikeChargerSceneController.on;Response.text("Garage bike charger on!") },
    Method.GET / "pluggaragebikeoff" -> handler{ GarageBikeChargerSceneController.off;Response.text("Garage bike charger off!") },
    Method.GET / "pluggarageauto" -> handler{BikePlugControl.startCharging(4);Response.text("auto charge at 4:00 a.m.") }
  ).sandbox


