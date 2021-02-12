package SparkBasics.lessons

import org.apache.spark.sql.SparkSession

object les1 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("my_first_spark_app")
    .master("local[1]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {


    test1
    //test2


  }

  // 1. calculate pi
  def test1() = {
    val numPoints = 1e7.toInt
    val ptCnt = 10

    //---------------
    //println()
    //println("BREAK POINT #1: press any key ...")
    //val ch1 = scala.io.StdIn.readLine()

    val ptNums = sc.range(0, numPoints,1, ptCnt)
    ptNums.persist()

    val points = ptNums
      .map( el => (2.0*math.random-1.0, 2.0*math.random-1.0) )

    val insidePoints = points
      .filter( el => (math.pow(el._1, 2.0) + math.pow(el._2, 2.0) <= 1.0) )

    //---------------
    //println()
    //println("BREAK POINT #2: press any key ...")
    //val ch2 = scala.io.StdIn.readLine()


    val insideCircRatio = insidePoints.count.toDouble / numPoints.toDouble

    val outsidePoints = points.filter(el => (math.pow(el._1, 2.0) + math.pow(el._2, 2.0) > 1.0))
    val outsideCircRatio = outsidePoints.count.toDouble / numPoints.toDouble

    //---------------
    println()
    println("BREAK POINT #3: press any key ...")
    val ch3 = scala.io.StdIn.readLine()

    val pi = insideCircRatio * 4.0
    println(pi)
  }

  // 2. process text file
  def test2() = {
    val lines = sc.textFile("data/war-and-peace/part-1.txt")
    val lls = lines.map( _.length )
    val loc = lls.collect
    loc.foreach(el => println(el))
  }



}
