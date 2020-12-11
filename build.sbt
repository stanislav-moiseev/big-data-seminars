name := "hello_scala"

version := "0.1"

//scalaVersion := "2.11.8"
scalaVersion := "2.12.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.0",
  "org.apache.spark" %% "spark-mllib" % "3.0.0"
)

libraryDependencies += "net.java.dev.jna" % "jna" % "4.0.0"
