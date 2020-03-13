package Demo

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SparkSession

import scala.math.Numeric

import L1_PiCalculator.local_pi
import L1_PiCalculator.spark_pi

import L2_CharWordCounter.count_words
import L2_CharWordCounter.count_letters

object Main {

  def get_park_session(): SparkSession = {

    val spark = SparkSession
      .builder()
      .appName("Test app")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    return spark
  }

  //----------------------------------------
  def main(args: Array[String]): Unit = {

 //    spark_test_4()
     spark_test_5()

    //while (true) {
    //  val aaa = 111
    //}
  }

  //-------------------------------------------
  def spark_test_1(): Unit = {

    val spark = get_park_session()
    val sc = spark.sparkContext

    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "../data/sample_libsvm_data.txt")
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a RandomForest model.
    // Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 3 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 32

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    println("Learned classification forest model:\n" + model.toDebugString)

    // Save and load model
    //model.save(sc, "target/tmp/myRandomForestClassificationModel")
    //val sameModel = RandomForestModel.load(sc, "target/tmp/myRandomForestClassificationModel")
  }

  //-------------------------------------------
  def spark_test_2(): Unit = {

    val spark = get_park_session()

    val num_points = 1e8.toInt

    //local_pi(num_points)
    spark_pi(num_points, spark )
  }

  //-------------------------------------------
  def spark_test_3(): Unit = {

    val spark = get_park_session()

    val fname = "data/war-and-peace/*"

    //count_words(spark, fname, "", 20, 20,  30)
    count_letters(spark, fname, 100)
  }

  //-------------------------------------------
  def spark_test_4(): Unit = {

    val spark = get_park_session()
    val fname = "data/graphs/0.txt"
    val cc = L4_GraphSearcher.ccomp(fname, spark.sparkContext, 1 )

    val aaa = 111



  }

  //-------------------------------------------
  def spark_test_5(): Unit = {

    val spark = get_park_session()
    val fname = "data/war-and-peace/*"
    val cc = L5_NextWordPredictor.predict_word( spark.sparkContext, fname, List("она","встала"), 2)

    //println()
    //println("BREAK POINT #2: press any key ...")
    //val ch = scala.io.StdIn.readLine()
  }

}
