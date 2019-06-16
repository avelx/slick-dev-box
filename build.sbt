name := "slick-dev-box"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.1",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1"
)

// H2 specific
//libraryDependencies += "com.h2database" % "h2" % "1.4.192"

// MySQL specific
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"