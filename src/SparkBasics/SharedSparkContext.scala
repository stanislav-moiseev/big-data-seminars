package SparkBasics

import org.apache.spark.sql.SparkSession

class SharedSparkContext {
  val spark = SharedSparkContext.get_spark_session()
  val sc = spark.sparkContext
}

object SharedSparkContext {

  def get_spark_session(): SparkSession = {

    val spark = SparkSession
      .builder()
      .appName("Test app")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    return spark
  }

}
