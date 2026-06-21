package org.aurora.api
import zio._
import zio.http._




object HelloRoutes: 

  val path: os.Path = os.pwd
  val indexHtmlPath = os.pwd / "target" / "docs" / "site" / "index.html"
  val docsBasePath = os.pwd / "target" / "docs" / "site"  

        

  val app = Routes(

    Method.GET / "" -> handler {Response.redirect(URL(Path.root / "docs" / "index.html"))},//(Path.root / "docs" /"index.html")) },
    Method.GET / "docs" ->  handler {Response.redirect(URL(Path.root / "docs" / "index.html")) }, //Handler.fromFile(indexHtmlPath.toIO   ),
    Method.GET / "log" -> handler{ 
      for {
        x <-ZIO.logInfo("Hello from log handler").as(Response.text("Logged a message!"))
      } yield x
    },
    Method.GET / "hello"        -> Handler.text("hello"),
    Method.GET / "hello" / string("name") -> 
      handler{ (name: String, _: Request) => Response.text(s"Hello, $name!") },
    Method.GET / "pwd"  -> handler{ Response.text(s"Current working directory: ${os.pwd}") },

  ).sandbox


