package org.aurora.api

import zio._
import zio.http._
import zio.json._

import zio.http.template._
import org.aurora.dto.Hello
import zio.http.Body.ContentType
import zio.http.Header.{CacheControl, Expires,Pragma}
import java.time.ZonedDateTime


package fileutils :
  

  def pathHandler:(Path,Response) =>  Handler[Any, Nothing, (Path, Request), Path] = (path, response) => Handler.param[(Path, Request)](_._1)
  def requestHandler:(Path,Response) =>  Handler[Any, Nothing, (Path, Request), Request] = (path, response) => Handler.param[(Path, Request)](_._2)

  val docsBasePath = os.pwd / "target" / "docs" / "site"  

  private def revisedPath(path:String): String =  
    if(path == "") s"$docsBasePath"
      else 
      {
        val revisedPath = docsBasePath / os.RelPath(path) 
        s"$revisedPath"
      }

  def staticFileHandler(path: Path): Handler[Any, Throwable, Request, Response] = 
    val encodedPath = path.encode
    val basePathRevised = revisedPath(encodedPath)
    val handler = Handler.fromFile(os.Path(basePathRevised).toIO )
    addExpirationHeaders(handler)

  def addExpirationHeaders(h: Handler[Any, Throwable, Request, Response])  =  
    h.addHeader(CacheControl.NoCache).addHeader(CacheControl.NoStore).addHeader(CacheControl.MustRevalidate)
     .addHeader(Pragma.NoCache).addHeader(Expires.apply(ZonedDateTime.now))

