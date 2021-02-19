package SparkBasics.lessons

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK

object les2 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("my_first_spark_app")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {

    test1 // 1. Discover jobs, stages, tasks

    // test2 // RDD persistance level

  }

  // 1. Discover jobs, stages, tasks
  def test1() = {
    val numPoints = 1e7.toInt
    val ptCnt = 10

    //----- a ----------
    val points = sc.range(0, numPoints,1, ptCnt)
      .persist( MEMORY_AND_DISK )
    val pointsCnt = points.count()

    //----- p=f(a) ----------
    val randPoints = points
      .map ( el=> (10*math.random).toInt )
      .zip( points )
      .reduceByKey( _+_ )
      .filter( el => el._2%2 == 1 )
    val randPointsProj = randPoints
      .map( el => el._2 )
      .persist( MEMORY_AND_DISK )
    val randPointsCnt = randPoints.count()

    //----- q=g(b) ----------
    val squaredAndFilteredPoints = points
      .map ( el=> math.pow( el, 2.0 ).toLong )
      .filter( el => el % 4 == 0 )
      .filter( el => el%3 == 0 )
      .persist( MEMORY_AND_DISK )
    val morePointsSum = squaredAndFilteredPoints.sum()

    //----- h(p,q) ----------
    val unionPoints = randPointsProj
      .union( squaredAndFilteredPoints )
    val unionPointsCnt = unionPoints.count()

    //---------------
    println()
    println("BREAK POINT FOR Spark UI: press any key ...")
    val ch = scala.io.StdIn.readLine()
  }

}
