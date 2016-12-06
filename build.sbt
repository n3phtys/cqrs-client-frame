enablePlugins(ScalaJSPlugin)
enablePlugins(Angulate2Plugin)

name := "cqrs-client-frame"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.github.lukajcb" %%% "rxscala-js" % "0.9.2"

libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.3"

libraryDependencies += "com.github.marklister" %%% "base64" % "0.2.3"

ngBootstrap := Some("nephtys.dualframe.cqrs.client.ClientFrameModule")

jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-rc.4" / "bundles/Rx.min.js" commonJSName "Rx"


scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.8", "-unchecked",
  "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint")