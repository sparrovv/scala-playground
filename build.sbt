import sbt.Keys.fullClasspath
import sbt.Test

val jacksonVersion = "2.10.1"

val commonSettings = Seq(
  scalaVersion := Version.scala212,
  organization := "com.mwrobel",
  organizationName := "Michal Wrobel",
  scalacOptions ++= List(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-encoding",
      "UTF-8"
    )
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "playground",
    publishArtifact := false
  )

lazy val scalaScripts = (project in file("scala-scripts"))
  .settings(
    commonSettings,
    name := "scala-scripts",
    version := "1.0.0-SNAPSHOT",
    libraryDependencies ++= Seq(
        "com.fasterxml.jackson.module"   %% "jackson-module-scala" % jacksonVersion,
        "com.fasterxml.jackson.core"     % "jackson-databind"      % jacksonVersion,
        "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonVersion,
        "com.lihaoyi"                    % "ammonite"              % "2.2.0" cross CrossVersion.full
      ),
    Test / sourceGenerators += Def.task {
        val file = (sourceManaged in Test).value / "amm.scala"
        IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
        Seq(file)
      }.taskValue,
    // Optional, required for the `source` command to work
    (Test / fullClasspath) ++= {
      (updateClassifiers in Test).value.configurations
        .find(_.configuration.name == Test.name)
        .get
        .modules
        .flatMap(_.artifacts)
        .collect { case (a, f) if a.classifier == Some("sources") => f }
    }
  )
