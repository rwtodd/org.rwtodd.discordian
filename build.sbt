ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version      := "1.0"
ThisBuild / organization := "org.rwtodd.discordian"

lazy val commonSettings = Seq(
	scalacOptions ++= Seq("-target:11")
)

lazy val discordian = (project in file("lib"))
	.settings(
		commonSettings,
		scalacOptions ++= Seq("-deprecation"),
		libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % Test
	)

lazy val ddate_cli = (project in file("cli"))
	.dependsOn(discordian)
	.settings(
		commonSettings
	)

// vim: filetype=sbt:noet:tabstop=4:autoindent
