name := "hello_scala"

version := "0.1"

scalaVersion := "2.11.8"
//scalaVersion := "2.13.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.2",
  "org.apache.spark" %% "spark-mllib" % "2.3.2"
)

