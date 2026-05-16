package org.aurora.api
import zio._
import zio.http._
import zio.json._

import zio.http.template._
import org.aurora.dto.Hello
import zio.http.Body.ContentType



object HelloRoutes: 
  import laika.api.Transformer
  import laika.format.{HTML, Markdown}
  import laika.config.SyntaxHighlighting

  private def htmlResponse(html: String): Response =
    Response(body = Body.fromString(html))
      .addHeader(Header.ContentType(MediaType.text.html))


  def readme(readmeContent: String) = 
    val transformer = Transformer
        .from(Markdown)
        .to(HTML)
        .using(Markdown.GitHubFlavor, SyntaxHighlighting)
        .build
    transformer.transform(readmeContent).toOption.getOrElse("Error transforming markdown")    
  val app = Routes(
    Method.GET / "" ->  handler{
      val s = readme(
         scala.io.Source.fromResource("README.md").mkString
      )
      htmlResponse(s)
        
      }      
    ,
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") },

  ) 
 