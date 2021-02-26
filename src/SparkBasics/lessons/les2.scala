package SparkBasics.lessons

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK

object les2 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_2")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {

    // test1 // 1. Discover jobs, stages, tasks

    // test2 // 2. RDD persistance level

    test3 // 3. Broadcast variables
  }

  // 1. Discover jobs, stages, tasks
  def test1() = {
    val numPoints = 1e7.toInt
    val ptCnt = 10

    //----- a ----------
    val points = sc.range(0, numPoints, 1, ptCnt)
    val pointsCnt = points.count()

    //----- p=f(a) ----------
    val randPoints = points
      .map(el => (10 * math.random).toInt)
      .zip(points)
      .reduceByKey(_ + _)
      .filter(el => el._2 % 2 == 1)
    val randPointsProj = randPoints
      .map(el => el._2)
    val randPointsCnt = randPointsProj.count()

    //----- q=g(b) ----------
    val squaredAndFilteredPoints = points
      .map(el => math.pow(el, 2.0).toLong)
      .filter(el => el % 4 == 0)
      .filter(el => el % 3 == 0)
    val morePointsSum = squaredAndFilteredPoints.reduce(_+_)

    //----- h(p,q) ----------
    val unionPoints = randPointsProj
      .union(squaredAndFilteredPoints)
    val unionPointsCnt = unionPoints.count()

    //---------------
    println()
    println("BREAK POINT FOR Spark UI: press any key ...")
    val ch = scala.io.StdIn.readLine()
  }

  def profile[R](code: => R, t: Long = System.nanoTime) =
    (code, System.nanoTime - t)

  def test2() = {

    val ptCnt = 10

    val lines = sc.textFile("data/bc/data.csv", ptCnt)
      .persist

    // count number of cells
    val (res1, dur1) = profile({
      val cellsCnt = lines
        .flatMap(line => line.split(","))
        .count
      println(s"File contains ${cellsCnt} cells.")
    })
    println(s"St1 complete in - ${dur1*1.0e-9} seconds.")

    // count number of chars
    val (res2, dur2) = profile({
      val charsCnt = lines
        .flatMap(line => line.toCharArray())
        .count
      println(s"File contains ${charsCnt} chars.")
    })
    println(s"St2 complete in - ${dur2*1.0e-9} seconds.")

    lines.unpersist()

    //---------------
    println()
    println("BREAK POINT FOR Spark UI: press any key ...")
    val ch = scala.io.StdIn.readLine()
  }

  def test3 = {

    val univs_map = Map(
      ("МГУ","Московский Государственный Университет им. М.В.Ломоносова"),
      ("МГТУ","Московский Государственный Технический Университет им. Н.Э.Баумана"),
      ("МФТИ","Московский Физико Технический Институт"),
      ("MIT", "Massachusetts Institute of Technology"),
      ("Caltech","California Institute of Technology")
    )
    val countries_map = Map(
      ("США","Соединенные Штаты Америки"),
      ("РФ","Российская Федерация"))

    //println("BREAK POINT #1: press any key ...")
    //val ch1 = scala.io.StdIn.readLine()

    // 1. build broadcast maps
    val bc_univs_map = sc.broadcast(univs_map)
    val bc_countries_map = sc.broadcast(countries_map)

    //println("BREAK POINT #2: press any key ...")
    //val ch2 = scala.io.StdIn.readLine()

    val data = Seq(("James","Smith","США","MIT"),
      ("Николай","Петров","РФ","МГУ"),
      ("Peter","Williams","США","MIT"),
      ("Мария","Иванова","РФ","МФТИ")
    )

    // 2. get distributed data
    val rdd1 = sc.parallelize(data, 4).cache()

    // DEBUG: print first 10 records from rdd1
    println("rdd1:")
    val res1 = rdd1.take(10)
    res1.foreach( el => println(el) )

    // 3. transform rdd using broadcast maps
    val rdd2 = rdd1.map( record => {
      val sh_country = record._3
      val sh_univ = record._4
      // with broadcast
//      val full_country = bc_countries_map.value.get(sh_country).get
  //    val full_univer = bc_univs_map.value.get(sh_univ).get

      // without broadcast
      val full_country = countries_map.get(sh_country).get
      val full_univer = univs_map.get(sh_univ).get
      (record._1,record._2,full_country,full_univer)
    })

    val rdd2_cnt = rdd2.count()

    // print first 10 records from rdd2
    println("rdd2:")
    val res2 = rdd2.take(10)
    res2.foreach( el => println(el) )

    println("BREAK POINT #3: press any key ...")
    val ch3 = scala.io.StdIn.readLine()
  }
}
