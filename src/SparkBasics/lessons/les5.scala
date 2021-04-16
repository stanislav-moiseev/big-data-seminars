package SparkBasics.lessons

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SparkSession

object les5 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_5")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {
    test1 //
  }

  def profile[R](code: => R, t: Long = System.nanoTime) =
    (code, System.nanoTime - t)

  def test1 = {
    // Load and parse the data file.
    val dataOr = MLUtils.loadLibSVMFile(sc, "data/ml/rcv1_train.binary")
    val sampleRatio = 0.1;
    val seed = 0;
    val data = dataOr
      .map( el => LabeledPoint( if (el.label < 0) 0 else el.label, el.features) )
      .sample(true, sampleRatio, seed)
      .cache

    val splits = data.randomSplit(Array(0.7, 0.3), seed)
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a RandomForest model.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 10
    val numTrees = 100
    val res = profile(RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
    )
    println(s"Model was trained in ${res._2*1.0e-9} seconds.")
    val model = res._1

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println(s"Test Error (on test dataset) = ${testErr*100.0} percents")

    println("BREAK POINT: press any key ...")
    val ch3 = scala.io.StdIn.readLine()

  }


}
