package SparkBasics.lessons

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}

object les6 {
  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_6")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {
    //test1
    test2
  }

  def profile[R](code: => R, t: Long = System.nanoTime) =
    (code, System.nanoTime - t)

  // building of ML-pipeline
  def test1 = {

    val data = Array(
      (1, "b a ll a a", 0.0),
      (2, "a b c d e a math", 1.0),
      (3, "b d b sdf sdf dfdf", 0.0),
      (4, "math f g h", 1.0),
      (5, "physics sdf dwq", 0.0)
    )
    val trainingData = spark.createDataFrame(data).toDF("num", "text", "label")
    //trainingData.take(3).foreach(println(_))

    val tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")
    //val addedTokens = tokenizer.transform(trainingData)
    //println("words:")
    //addedTokens.select("num", "words").take(3).foreach(println(_))

    val hashingTF = new HashingTF()
      .setNumFeatures(1000)
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("features")
    //val addedFeatures = hashingTF.transform(addedTokens)
    //println("features:")
    //addedFeatures.select("features").take(5).foreach(println(_))

    val lr = new LogisticRegression()
      .setMaxIter(10)

    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, hashingTF, lr))

    val model = pipeline.fit(trainingData)

    val testData = spark.createDataFrame(Seq(
      (1, "math i j k"),
      (2, "l m n ppjshjw"),
      (3, "paper scissors rock"),
      (4, "discreete math")
    )).toDF("num", "text")

    // Make predictions on test documents.
    model.transform(testData)
      .select("num", "text", "probability", "prediction")
      .collect()
      .foreach { case Row(num: Int, text: String, prob: Vector, prediction: Double) =>
        println(s"($num, $text) --> prob=$prob, prediction=$prediction")
      }
  }

  // getSentences from text file
  def getSentences( filename: String, minWordsCnt: Int, minWordLength: Int) = {
    val sentences_rdd: RDD[Array[String]] = sc
      .textFile(filename)
      .map(_.toLowerCase)
      .flatMap(line => line.split("[.…?!]"))
      .map(sentence =>
        sentence
          .split("[  ,;:–—«»()\\[\\]]")
          .filter(word => word.length >= minWordLength))
      .filter( el => el.length >= minWordsCnt )
    sentences_rdd
  }

  // classify Tolstoy from Pushkin
  def test2 = {

    val minWordsCnt = 5
    val minWordLength = 3

    val ds1 = getSentences("data/war-and-peace/*", minWordsCnt, minWordLength)
    val ds2 = getSentences("data/pushkin/*", minWordsCnt, minWordLength)

    println("Tolstoy sentences:")
    ds1.take(3).foreach(el => println(el.mkString(" ")))
    val ds1c = ds1.count()
    println(s"total: ${ds1c}")

    println("Pushkin sentences:")
    ds2.take(3).foreach(el => println(el.mkString(" ")))
    val ds2c = ds2.count()
    println(s"total: ${ds2c}")

    val df1 = spark.createDataFrame(
      ds1.zipWithIndex().map(_.swap).map(el => (el._1, el._2, 1.0))).toDF("id", "words", "label")
    val df2 = spark.createDataFrame(
      ds2.zipWithIndex().map(_.swap).map(el => (el._1, el._2, 0.0))).toDF("id", "words", "label")

    val data = df1.union(df2)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    // start building the pipeline
    val hashingTF = new HashingTF()
      .setNumFeatures(4000)
      .setInputCol("words")
      .setOutputCol("features")
    val lr = new LogisticRegression()
      .setMaxIter(20)
    val pipeline = new Pipeline()
      .setStages(Array(hashingTF, lr))
    val model = pipeline.fit(trainingData)

    // test model's performance
    val res = model.transform(testData)
      .select("id", "words", "prediction", "label")
      .collect()
    //preds.take(10).foreach(println(_))
    val errors = res.map(el =>
      if (el.get(2) == el.get(3)) 0.0 else 1.0)
    val errRate = errors.sum / errors.length.toDouble
    println(s"errorRate: ${errRate}")
  }

}
