package SparkBasics.lessons

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.mllib.linalg.{Matrices, Matrix, Vector, Vectors}
import org.apache.spark.mllib.random.RandomRDDs._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

// MLLib: basic statistics
object les7 {
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
    //test2
    test3
  }

  // 1. data conversion + summary statistics
  def test1: Unit = {

//    // create DataFrame from Seq
//    val data = Seq((1,"a"), (2,"b"), (3,"c"))
//    val df0 = spark.createDataFrame(data).toDF("num", "char")
//    df0.show()
//    df0.printSchema()
//    val cnt0 = df0.rdd

    // read data
    val df1 = spark.read
      .option("header", true)
      .csv("data/ml/titanic.csv")
    df1.show()
    df1.printSchema()
    df1.describe("Age", "Sex", "Survived").show

    // create sex converer
    def sConvert = (s: String) => if (s == "male") 1 else 0
    val udf1 = spark.udf.register("sexConverter", sConvert)

    // create new dataframe
    val df2 = df1
      .withColumn("Age", col("Age").cast("int"))
      .withColumn("Sex", udf1(col("Sex")))
      .withColumn("Survived", col("Survived").cast("int"))
      .withColumn("Pclass", col("Pclass").cast("int"))
      .withColumn("PassengerId", col("PassengerId").cast("int"))
    df2.show()
    df2.printSchema()
    df2.describe("Age", "Sex", "Survived").show

    // get some correlations
    println(s"corr_age_surv - ${df2.stat.corr("Age", "Survived")}")
    println(s"corr_sex_surv - ${df2.stat.corr("Sex", "Survived")}")
    println(s"corr_pclass_surv - ${df2.stat.corr("Pclass", "Survived")}")
    println(s"corr_id_surv - ${df2.stat.corr("PassengerId", "Survived")}")

    val ch1 = scala.io.StdIn.readLine()
  }

  // 2. stratified sampling
  def test2: Unit = {
    // an RDD[(K, V)] of any key value pairs
    val data = sc.parallelize(
      Seq((1, '1'),
        (1, '2'),
        (1, '3'),
        (2, '4'),
        (2, '5'),
        (2, '6'),
        (3, '7'),
        (3, '8'),
        (3, '9')
      ))

    // specify the exact fraction desired from each key
    val fractions = Map(1 -> 0.2, 2 -> 0.2, 3 -> 0.6)

    // Get an approximate sample from each stratum
    val seed = 0
    val approxSample = data.sampleByKey(withReplacement = true, fractions, seed)
    println("approx_sampling:")
    approxSample.collect().foreach(el => println(el))

    // Get an exact sample from each stratum
    val exactSample = data.sampleByKeyExact(withReplacement = true, fractions, seed)
    println("exact_sampling:")
    exactSample.collect().foreach(el => println(el))

    val ch1 = scala.io.StdIn.readLine()
  }

  // generation of randomly distributed rdds
  def test3: Unit = {
    // Generate a random double RDD that contains 1 million i.i.d. values drawn from the
    // standard normal distribution `N(0, 1)`, evenly distributed in 10 partitions.
    val u = normalRDD(sc, 100000, 10, 0 )
    // Apply a transform to get a random double RDD following `N(1, 4)`.
    val v = u.map(x => 1.0 + 2.0 * x)

    val first100=v.take(100).foreach(el=>println(el))
    val ch1 = scala.io.StdIn.readLine()
  }



}