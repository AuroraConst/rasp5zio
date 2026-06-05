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

  val path: os.Path = os.pwd
  val indexHtmlPath = os.pwd / "target" / "docs" / "site" / "index.html"
  val docsBasePath = os.pwd / "target" / "docs" / "site"  


  private def htmlResponse(html: String): Response =
    Response(body = Body.fromString(html))
      .addHeader(Header.ContentType(MediaType.text.html))

  private def docsHandler() = 
    handler{
      val extractPath    = Handler.param[(Path, Request)](_._1)
      val extractRequest = Handler.param[(Path, Request)](_._2)

      
      for{
         path <- extractPath 
         result <- {
          val p = os.RelPath( s"$path")
          val basePathRevised =  if(p.toString == "") {docsBasePath}
            else docsBasePath / os.RelPath("/")
          val finalPath =   basePathRevised / p 
          Handler.fromFile(finalPath.toIO  )
         }
      } yield result

    }

  def readme(readmeContent: String) = 
    val transformer = Transformer
        .from(Markdown)
        .to(HTML)
        .using(Markdown.GitHubFlavor, SyntaxHighlighting)
        .build
    transformer.transform(readmeContent).toOption.getOrElse("Error transforming markdown")    

  import zio.http.Middleware.*
  import zio.http.Header.{AccessControlAllowOrigin, Origin}
    
  val app = Routes(

    Method.GET / "" -> handler {Response.redirect(URL(Path.root / "docs" /"index.html")) },
    Method.GET / "docs" ->  handler {Response.redirect(URL(Path.root / "docs" / "index.html")) }, //Handler.fromFile(indexHtmlPath.toIO   ),
    Method.GET / "docs" / trailing -> docsHandler(),
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") },
    Method.GET / "pwd"  -> handler{ Response.text(s"Current working directory: ${os.pwd}") },

  ).sandbox  @@ cors(config)


  
  val config: CorsConfig =
    CorsConfig(
      allowedOrigin = {
        case origin if origin == Origin.parse("http://localhost:8080").toOption.get =>
          Some(AccessControlAllowOrigin.Specific(origin))
        case _                                                                      => None
      },
    )

 