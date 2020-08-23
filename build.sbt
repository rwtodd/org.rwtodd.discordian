ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version      := "1.0"
ThisBuild / organization := "org.rwtodd"

lazy val commonSettings = Seq(
	scalacOptions ++= Seq("-target:11")
)

lazy val discordian = (project in file("lib"))
	.settings(
		commonSettings,
		scalacOptions ++= Seq("-deprecation")
	)

lazy val ddate_cli = (project in file("cli"))
	.dependsOn(discordian)
	.settings(
		commonSettings
	)

// vim: filetype=sbt:noet:tabstop=4:autoindent
