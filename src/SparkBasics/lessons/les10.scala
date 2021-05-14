package SparkBasics.lessons

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{BlockMatrix, CoordinateMatrix, MatrixEntry, _}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.collection.mutable
import scala.util.Random

// PairedRDD
object les10 {

  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_9")
    .master("local[*]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  // Key-Valued RDD Operations
  def main(args: Array[String]): Unit = {
    //test1
    //test2
    test3
  }

  // 1. basic PairedRDD operations
  def test1 = {
    Random.setSeed(0)

    val SIZE = 30
    val PT_CNT = 10
    val data = (0 until SIZE)
      .map{ el => (Random.nextInt(10), Random.nextInt(100)) }
    val dataRDD = sc.parallelize( data, PT_CNT ).cache

    println("RDD:")
    dataRDD.collect().foreach(println(_))

    println("Reduction")
    val keysCnt = dataRDD.map{ case (k,v) => (k,1) }.reduceByKey( _+_ )
    keysCnt.collect().foreach(println(_))
    println()

    println("Groupping")
    val grouppedRDD = dataRDD.groupByKey()
    grouppedRDD.collect().foreach{
      case (key, els) => {
        println(key)
        print("---")
        print(els)
        println("")
      }
    }
    println()

    println("Sorting")
    println()
    val sortedRDD = dataRDD.sortByKey()
    sortedRDD.collect().foreach(println(_))

    //---------------
    println()
    println("BREAK POINT: press any key ...")
    val ch2 = scala.io.StdIn.readLine()
  }

  // 2. joining of PairedRDDs
  def test2 = {
    // RDD1 - K,V
    val codeRowsCnt = sc.parallelize(Seq(
      ("Ivan", 240),
      ("Ivan", 50),
      ("Andrey", 50),
      ("Petr", 39),
      ("Elena", 290),
      ("Katya", 300))).groupByKey()

    // RDD2 - K,W
    val programmerLangs = sc.parallelize(Seq(
      ("Ivan", "Java"),
      ("Elena", "Scala"),
      ("Elena", "Pascal"),
      ("Petr", "Scala"),
      ("Mary", "C++"))).groupByKey()

    println("case 1: join (inner join)")
    println("----------------------------")
    codeRowsCnt
      .join(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 2: left outer join")
    println("----------------------------")
    codeRowsCnt
      .leftOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 3: right outer join")
    println("----------------------------")
    codeRowsCnt
      .rightOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 4: full outer join")
    println("----------------------------")
    codeRowsCnt
      .fullOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()
  }

  // 3. cartesian poduct
  def test3 = {
    // RDD1 - K,V
    val r1 = sc.parallelize(Seq(
      ("Ivan", "aaa"),
      ("Ivan", "bbb"),
      ("Andrey", "ccc")))

    // RDD2 - K,W
    val r2 = sc.parallelize(Seq(
      ("Anna", 10),
      ("Maria", 20)))

    val r3 = r1.cartesian(r2)
    r3.collect().foreach(println(_))

    //---------------
    println()
    println("BREAK POINT: press any key ...")
    val ch2 = scala.io.StdIn.readLine()
  }
}
