package org.aurora

import zio.*
import zio.config._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider
import zio.{Config, ZIO, ZIOAppDefault}
import java.nio.file.Paths
import configuration.*



package object configuration :

// Configuration case class
  case class AppConfig(
    ETHERSCAN_API_KEY: String,
    COINGECK_API_KEY: String,
    CRYPTOAPI_KEY: String,
    BLOCKSTREAM_API_KEY: String
  )

  val appConfigLayer: TaskLayer[AppConfig] = ZLayer.fromZIO{
    val f = os.root / "dev" / "projects" / "SCALA" / "myzio" / "client" / "src" / "main" / "resources" / "application.conf"
    TypesafeConfigProvider.fromHoconFile(f.toIO).load(deriveConfig[AppConfig])
    // TypesafeConfigProvider.fromResourcePath().load(deriveConfig[AppConfig])
  }


  val program = 
    for {
      _           <- Console.printLine("getting configuration...")
      appConfig   <- ZIO.service[AppConfig]
      _          <- Console.printLine(s"Configuration loaded: $appConfig")


    } yield {}



