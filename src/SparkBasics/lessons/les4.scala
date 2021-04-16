package SparkBasics.lessons

import org.apache.spark.ml.classification.LinearSVC
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.sql.SparkSession

object les4 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_4")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {
    //test1 // Train Decision Tree classifier and save it
    //test2 // Load DT classifier and test in on a whole dataset
    test3 // Train Random Forest classifier
    //test4 // Train SVM classifier
  }

  def test1 = {
    // Load and parse the data file.
    val dataOr = MLUtils.loadLibSVMFile(sc, "data/ml/mushrooms.txt")
    val data = dataOr.map( el => LabeledPoint(el.label-1, el.features) )
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a RandomForest model.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 3
    val maxBins = 2
    val model = DecisionTree.trainClassifier(trainingData, numClasses,
      categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error (on test dataset) = " + testErr)
    println("DT:\n" + model.toDebugString)
    model.save(sc, "D:\\_tmp\\dt_model")
  }

  def test2 = {

    // Load and parse the data file.
    val dataOr = MLUtils.loadLibSVMFile(sc, "data/ml/mushrooms.txt")
    val data = dataOr.map( el => LabeledPoint(el.label-1, el.features) )

    // load classifier
    val model = DecisionTreeModel.load(sc, "D:\\_tmp\\dt_model")

    // Evaluate model on all data instances
    val labelAndPreds = data.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / data.count()
    println("Test Error (on whole dataset) = " + testErr)
  }

  def test3 = {
    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "data/ml/sample_libsvm_data.txt")
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a RandomForest model.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 3 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 16

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    println("RF:\n" + model.toDebugString)
  }

  def test4 = {
    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "data/ml/sample_libsvm_data.txt")
    println(s"Instances count: ${data.count()}")
    val firstPoint = data.take(1)(0)

    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train SVM model.
    val numIterations = 10
    val model = SVMWithSGD.train(trainingData, numIterations)
    //model.clearThreshold()

    // Evaluate model on test instances and compute test error
    val scoresAndLabels = testData.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

    val merr = scoresAndLabels.map( p => if (p._1 != p._2) 1 else 0 ).sum() / testData.count()
    println( s"Mean error: $merr" )

    // print first 10 scores and labels
    scoresAndLabels.take(10).foreach( println(_))

    val metrics = new BinaryClassificationMetrics(scoresAndLabels)
    val auROC = metrics.areaUnderROC()
    println(s"Area under ROC = $auROC")

    println("BREAK POINT: press any key ...")
    val ch3 = scala.io.StdIn.readLine()
  }


  }
