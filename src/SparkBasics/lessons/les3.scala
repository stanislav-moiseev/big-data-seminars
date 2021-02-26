package SparkBasics.lessons

import org.apache.spark.sql.SparkSession

object les3 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_3")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def profile[R](code: => R, t: Long = System.nanoTime) =
    (code, System.nanoTime - t)

  def main(args: Array[String]): Unit = {

    // test1 // 1. Accumulators

    // test2 // 2. Closures

    test3 // 3. Dataframes

  }

  def test1 = {

    val numPoints = 1e6.toInt
    val numPt = 10
    val points = sc.range(0, numPoints, 1, numPt)
    val accum = sc.longAccumulator("My Accumulator #1")

    val data = points
      .map(el => (10 * math.random).toInt)
      .zip(points)
      .map( el => el.swap )
      .cache      // don't forget to cache RDDs that are used many times

    // option 1:
    val elsCnt = data.filter( el => el._2 > 5 ).count()
    println(s"Opt1: number of els > 5 is ${elsCnt}")

    // option 2:
    data.foreach( el => if (el._2 > 5) accum.add( 1 ) )
    println(s"Opt2: number of els > 5 is ${accum.value}")

    // DBG:
//    val updRdd = data.mapPartitions( it => {
//      val partEls = it.toArray
//      val zero = 0
//      val test = 12 / zero
//
//      val newData = partEls.filter(el => el._2 > test)
//
//      newData.iterator
//    })
//    updRdd.count()
//    println(s"Opt2: number of els > 5 is ${accum.value}")
//
    println("BREAK POINT: press any key ...")
    val ch3 = scala.io.StdIn.readLine()
  }

  def test2 = {

    val numPoints = 100.toInt
    val numPt = 3
    val points = sc.range(0, numPoints, 1, numPt)
    val data = points.map(el => 1).cache()
    println("Data size: " + data.count())
    data.take(10).foreach(println(_))

    // What's wrong with this code?
    var counter = 0
    val res = data.mapPartitions(it => {
      var ptCounter = 0
      val ptData = it.toArray
      ptData.foreach(x => ptCounter += x)
      counter += ptCounter
      Array.fill(1)((ptCounter, counter)).iterator
    })
    res.foreach(el => println(el))
    println("Counter value: " + counter)

    val dataLocal = data.collect()
    dataLocal.foreach(x => counter = counter + x)
    println("Counter value: " + counter)

    println("BREAK POINT: press any key ...")
    val ch3 = scala.io.StdIn.readLine()
  }

  def test3 = {
    val df = spark.read
      .option("header",true)
      .csv("data/ml/titanic.csv" )

    df.show()
    df.printSchema()

    //val res = df.rdd.first()

    // Select one column
    val c10 = df.select("Age", "Name", "Fare")
    c10.show()

    // group by
    df.groupBy("Age").count().show()

    // create temp view
    df.createOrReplaceGlobalTempView("titanic")

    // use SQL on temp views
    val newDf = spark.sql("select a.Name, a.Age from global_temp.titanic as a")
    newDf.show()

    println("BREAK POINT: press any key ...")
    val ch3 = scala.io.StdIn.readLine()
  }
}
