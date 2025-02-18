import sbtassembly.AssemblyPlugin // ✅ Import the plugin

val scala3Version = "3.6.3"

lazy val root = project
  .in(file("."))
  .enablePlugins(AssemblyPlugin) // ✅ Enable sbt-assembly
  .settings(
    name := "Scala Todo CLI",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,

    assembly / assemblyOutputPath := file("./todo.jar")
  )
