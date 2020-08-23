ThisBuild / scalaVersion :="2.13.3"
ThisBuild / organization := "org.rwtodd"

lazy val discordian = (project in file("."))
	.settings(
		scalacOptions ++= Seq("-deprecation")
	)

// vim: filetype=sbt:noet:tabstop=4:autoindent
