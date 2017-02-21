/**
 *
 * Projects
 *
 */

lazy val `example` =
  project.in(file("."))
  .aggregate(common, client, server)
  .settings(commonSettings: _*)
  .enablePlugins(GitVersioning)

lazy val common =
  project.in(file("common"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibraries)
  .settings(name := "common")
  .enablePlugins(GitVersioning)

lazy val client =
  project.in(file("client"))
  .dependsOn(common)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibraries)
  .settings(name := "client")
  .enablePlugins(JavaAppPackaging, GitVersioning)

lazy val server =
  project.in(file("server"))
  .dependsOn(common)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibraries)
  .settings(name := "server")
  .enablePlugins(JavaAppPackaging, GitVersioning)


lazy val commonSettings = Seq(
  organization := "com.github.jw3",
  name := "example-scala-openshift",
  git.useGitDescribe := true,
  scalaVersion := "2.12.1",

  scalacOptions ++= Seq(
    "-encoding", "UTF-8",

    "-feature",
    "-unchecked",
    "-deprecation",

    "-language:postfixOps",
    "-language:implicitConversions",

    "-Ywarn-unused-import",
    "-Xfatal-warnings",
    "-Xlint:_"
  )
)

lazy val commonLibraries = {
  val akkaVersion = "2.4.17"
  val akkaHttpVersion = "10.0.3"
  val scalatestVersion = "3.0.1"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,

    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.9",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  )
}
