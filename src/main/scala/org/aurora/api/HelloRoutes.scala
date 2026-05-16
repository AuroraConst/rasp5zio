package org.aurora.api
import zio._
import zio.http._
import zio.json._

import zio.http.template._
import org.aurora.dto.Hello



object HelloRoutes: 
  import laika.api.Transformer
  import laika.format.{HTML, Markdown}
  def readme(readmeContent: String) = 
    val transformer = Transformer
        .from(Markdown)
        .to(HTML)
        .build
    transformer.transform(readmeContent).toOption.getOrElse("Error transforming markdown")    
  val app = Routes(
    Method.GET / "" ->  handler{
      val s = readme(
         scala.io.Source.fromResource("README.md").mkString
      )
      println(s)
      val html = """<!DOCTYPE html> <html><head><title>README</title></head><body>""" + s + """</body></html>"""
      Response.html(Html.fromString(s))
        
      }      
    ,
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") }
  )
 
  