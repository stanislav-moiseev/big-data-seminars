package SparkBasics

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SparkSession

import scala.math.Numeric
import PiCalculator.local_pi
import PiCalculator.spark_pi
import CharWordCounter.count_words
import CharWordCounter.count_letters
import SparkSerialization.SerializationTests
import math.random

object Main extends SharedSparkContext {

  //----------------------------------------
  def main(args: Array[String]): Unit = {
     // test_1()
     test_2()
     // test_3()
     // test_4()
     // test_5()
    //test_6()

    // test_7()

    val data = scala.collection.immutable.Vector[Int](1,2,3)
    data.apply(2)





  }

  def test_7(): Unit = {

    val n = 1000
    val pt_cnt = 10
    val nums = spark.sparkContext.range(0, n, 1, pt_cnt);

    val r_nums = nums.map (i => math.floor(random * 2000.0) )

    val topXNumbers = r_nums
      .filter(_ > 1000 ) //Stage 1
      .map(value => (value, 1)) // Stage 1

      .groupByKey() //Stage 2
      .map(value => (value._1, value._2.sum)) //Stage 2

      .sortBy(_._2, false) //Stage 3
      .count() // Stage 3

    println()
    println("BREAK POINT #1: press any key ...")
    val ch = scala.io.StdIn.readLine()

  }

  //-------------------------------------------
  // Estimate PI-number
  def test_1(): Unit = {
    val num_points = 1e8.toInt
    //local_pi(num_points)
    spark_pi(num_points, spark )
  }

  //-------------------------------------------
  // Count words in text file (files)
  def test_2(): Unit = {
    val fname = "data/war-and-peace/*"
    //count_words(spark, fname, "", 20, 20,  30)
    count_letters(spark, fname, 100)
  }

  //-------------------------------------------
  // Search of connected components in graph
  def test_3(): Unit = {

    val fname = "data/graphs/0.txt"
    val cc = GraphSearcher.ccomp(fname, spark.sparkContext, 1 )

    val aaa = 111

  }

  //-------------------------------------------
  // Predict next word
  def test_4(): Unit = {
    val fname = "data/war-and-peace/*"
    val cc = NextWordPredictor.predict_word( spark.sparkContext, fname, List("здравствуйте"), 2)

    //println()
    //println("BREAK POINT #2: press any key ...")
    //val ch = scala.io.StdIn.readLine()
  }

  //-------------------------------------------
  // Search palindromes in text files
  def test_5(): Unit = {
    val fname = "data/war-and-peace/*"
    val cc = NextWordPredictor.searchForPalindromes( spark.sparkContext, fname, 3)
  }

  //-------------------------------------------
  // test of broadcast variables
  def test_6(): Unit = {
    BroadCast.test( spark.sparkContext )
  }

}
