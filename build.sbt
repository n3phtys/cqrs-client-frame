import sbt.ProjectRef

enablePlugins(ScalaJSPlugin)
enablePlugins(Angulate2Plugin)

name := "cqrs-client-frame"

version := "0.0.1"

scalaVersion := "2.12.1"



val genericProtocolurl =  "https://github.com/n3phtys/cqrs-dual-frame.git"


lazy val genericProtocolProject = ProjectRef(uri(genericProtocolurl), "cqrsdualframeJS")


lazy val root = Project("cqrs-client-frame", file("."))
  .settings(
    publish := {},
    publishLocal := {}
  )
  .dependsOn(genericProtocolProject)
  .settings(
    libraryDependencies ++= Seq(
      "com.github.lukajcb" %%% "rxscala-js" % "0.9.2",
      "com.lihaoyi" %%% "upickle" % "0.4.4",
      "com.github.marklister" %%% "base64" % "0.2.3",
      "de.nephtys" %%% "scalajs-google-sign-in" % "0.0.1"
    ),
    ngBootstrap := Some("nephtys.dualframe.cqrs.client.AppModule"),
    scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.8", "-unchecked",
      "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint"),
    jsDependencies += "org.webjars.npm" % "rxjs" % "5.0.0-rc.4" / "bundles/Rx.min.js" commonJSName "Rx",
      jsDependencies += "org.webjars" % "jquery" % "2.1.3" / "2.1.3/jquery.js"

  )
