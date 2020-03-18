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

object Main extends SharedSparkContext {

  //----------------------------------------
  def main(args: Array[String]): Unit = {

     //spark_test_1()
     //spark_test_2()
     //spark_test_3()
     //spark_test_4()
    //spark_test_5()

     SerializationTests.test_2( sc )

  }

  //-------------------------------------------
  def spark_test_1(): Unit = {
    val num_points = 1e8.toInt
    //local_pi(num_points)
    spark_pi(num_points, spark )
  }

  //-------------------------------------------
  def spark_test_2(): Unit = {
    val fname = "data/war-and-peace/*"
    //count_words(spark, fname, "", 20, 20,  30)
    count_letters(spark, fname, 100)
  }

  //-------------------------------------------
  def spark_test_3(): Unit = {
    val fname = "data/graphs/0.txt"
    val cc = GraphSearcher.ccomp(fname, spark.sparkContext, 1 )
  }

  //-------------------------------------------
  def spark_test_4(): Unit = {
    val fname = "data/war-and-peace/*"
    val cc = NextWordPredictor.predict_word( spark.sparkContext, fname, List("она","встала"), 2)

    //println()
    //println("BREAK POINT #2: press any key ...")
    //val ch = scala.io.StdIn.readLine()
  }

  //-------------------------------------------
  def spark_test_5(): Unit = {
    val fname = "data/war-and-peace/*"
    val cc = NextWordPredictor.searchForPalindromes( spark.sparkContext, fname, 3)
  }

}
