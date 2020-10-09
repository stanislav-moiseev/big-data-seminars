package MLBasics

import SparkBasics.SharedSparkContext
import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.types._

object DFTests extends SharedSparkContext{

  //----------------------------------------
  def main(args: Array[String]): Unit = {
    test_1( )
  }

  // This example shows how to read DF and apply basic operations to it
  def test_1( ): Unit = {

    val sch = StructType( Array(
      StructField("my_timestamp",TimestampType,true),
      StructField("age",LongType,true),
      StructField("gender",StringType,true),
      StructField("country",StringType,true),
      StructField("state",StringType,true),
      StructField("self_employed",StringType,true),
      StructField("family_history",StringType,true),
      StructField("treatment",StringType,true),
      StructField("work_interfere",StringType,true),
      StructField("no_employees",StringType,true),
      StructField("remote_work",StringType,true),
      StructField("tech_company",StringType,true),
      StructField("benefits",StringType,true),
      StructField("care_options",StringType,true),
      StructField("wellness_program",StringType,true),
      StructField("seek_help",StringType,true),
      StructField("anonymity",StringType,true),
      StructField("leave",StringType,true),
      StructField("mental_health_consequence",StringType,true),
      StructField("phys_health_consequence",StringType,true),
      StructField("coworkers",StringType,true),
      StructField("supervisor",StringType,true),
      StructField("mental_health_interview",StringType,true),
      StructField("phys_health_interview",StringType,true),
      StructField("mental_vs_physical",StringType,true),
      StructField("obs_consequence",StringType,true),
      StructField("comments",StringType,true) ))

    val df: DataFrame = spark
      .read
      .format("csv")
      .schema(sch)
      .option("header", true)
      //.option("inferSchema", true)
      .option("timestampFormat", "yyyy-MM-dd'T'HH:mm:ss")
      .option("mode", "failfast")
      .option("nullValue", "NA")
      .load("D:/01_big_data/big-data-seminars/data/ml/datasets_311_673_survey.csv")

    println(df.schema)

    // get subset
    val subset_df = df
      .select( "gender","my_timestamp", "age", "remote_work", "leave", "benefits")
      .filter("(age > 30) AND (gender=\"Female\")")

    val subset_df_2 = df
      .select( "gender","my_timestamp", "age", "remote_work", "leave", "benefits")
      .filter("(age > 30) AND (gender=\"Male\")")


    subset_df.show()

    //val aaa = 111

    //val subset_rows = subset_df.rdd.collect()
    subset_df.createOrReplaceTempView("my_tbl_f")
    subset_df_2.createOrReplaceTempView("my_tbl_m")
    //subset_df.createOrReplaceGlobalTempView("my_tbl_global")

    //
    println("List of tables:")
    spark.catalog.listTables().show()

   //


    // execute sql
    val sql_res = spark.sql(
      "select * " +
      "from my_tbl_f " +
      "where age>40 and benefits=\"Yes\" ")
    sql_res.show()

    // save results
   sql_res
      .write
      .format("csv")
      .option("header", true)
      .option("timestampFormat", "yyyy-MM-dd'T'HH:mm:ss")
      .option("mode", "overwrite")
      .save("D:/01_big_data/big-data-seminars/data/ml/sql_res.csv")

    // save results
    sql_res
      .write
      .format("json")
      .save("D:/01_big_data/big-data-seminars/data/ml/sql_res.json")

  }

}
