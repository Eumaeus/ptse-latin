name := "PSTE-Latin"

crossScalaVersions in ThisBuild := Seq("2.10.6","2.11.8", "2.12.4")
scalaVersion := (crossScalaVersions in ThisBuild).value.last


lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}

    )

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")
resolvers += Resolver.bintrayRepo("eumaeus", "maven")

lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "ptselat",
      organization := "edu.holycross.shot",
      version := "1.0.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
        "edu.holycross.shot.cite" %%% "xcite" % "3.3.0",
        "edu.holycross.shot" %%% "cex" % "6.2.1",
        "edu.holycross.shot" %%% "latphone" % "1.3.0" from "file:///Users/cblackwell/Dropbox/CITE/scala/unmanaged_jars/latphone_sjs0.6_2.12-1.3.0.jar",
        "edu.holycross.shot" %%% "ohco2" % "10.8.0"
      )
    ).
    jvmSettings(
      tutTargetDirectory := file("docs"),
      tutSourceDirectory := file("shared/src/main/tut")
    ).
    jsSettings(
      skip in packageJSDependencies := false,
      scalaJSUseMainModuleInitializer in Compile := true
    )

lazy val crossedJVM = crossed.jvm.enablePlugins(TutPlugin)
lazy val crossedJS = crossed.js
