val zioVersion            = "2.0.13"
val zioJsonVersion        = "0.5.0"
val zioConfigVersion      = "3.0.7"
val zioLoggingVersion     = "2.1.11"
val logbackClassicVersion = "1.4.7"
val postgresqlVersion     = "42.6.0"
val testContainersVersion = "0.40.15"
val zioMockVersion        = "1.0.0-RC11"
val zioHttpVersion        = "3.0.0-RC1"
val quillVersion          = "4.6.0.1"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        organization := "org.aurora",
        scalaVersion := "3.3.7",
      )
    ),
    name           := "rasp5zio",
    libraryDependencies ++= Seq(
      "io.getquill"   %% "quill-jdbc-zio"      % quillVersion excludeAll (
        ExclusionRule(organization = "org.scala-lang.modules")
      ),
      "org.postgresql" % "postgresql"          % postgresqlVersion,
      "dev.zio"       %% "zio"                 % zioVersion,
      "dev.zio"       %% "zio-streams"         % zioVersion,
      "dev.zio"       %% "zio-http"            % zioHttpVersion,
      "dev.zio"       %% "zio-config"          % zioConfigVersion,
      "dev.zio"       %% "zio-config-typesafe" % zioConfigVersion,
      "ch.qos.logback" % "logback-classic"     % logbackClassicVersion,
      "dev.zio"       %% "zio-json"            % zioJsonVersion,

      // logging
      "dev.zio"       %% "zio-logging"       % zioLoggingVersion,
      "dev.zio"       %% "zio-logging-slf4j" % zioLoggingVersion,
      "ch.qos.logback" % "logback-classic"   % logbackClassicVersion,

      // test
      "dev.zio"      %% "zio-test"                        % zioVersion            % Test,
      "dev.zio"      %% "zio-test-sbt"                    % zioVersion            % Test,
      "dev.zio"      %% "zio-test-junit"                  % zioVersion            % Test,
      "dev.zio"      %% "zio-mock"                        % zioMockVersion        % Test,
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,
      "dev.zio"      %% "zio-test-magnolia"               % zioVersion            % Test,
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
  )
  .enablePlugins(JavaAppPackaging)
  .dependsOn(dto)


lazy val dto = (project in file("dto"))
  .settings(
    name := "myziotemp-dto",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-json" % zioJsonVersion
    )
  )


lazy val client = (project in file("client"))
  .enablePlugins(GraalVMNativeImagePlugin)  
  .settings(
    name := "ziotemp-client",
    fork := true,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "os-lib" % "0.11.7",
      "dev.zio" %% "zio"      % zioVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      "com.softwaremill.sttp.client4" %% "zio" % "4.0.9" ,
      "com.softwaremill.sttp.client4"%%"core"%"4.0.9",
      "org.typelevel" %% "cats-effect" % "3.5.2",
      "com.typesafe" % "config" % "1.4.3",
      "dev.zio" %% "zio-config" % "4.0.4",
      "dev.zio" %% "zio-config-typesafe" % "4.0.4",
      "dev.zio" %% "zio-config-magnolia" % "4.0.4",
    )
  )
   .dependsOn(dto)

