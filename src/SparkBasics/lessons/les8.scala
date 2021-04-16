package SparkBasics.lessons

import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, IndexedRow, IndexedRowMatrix, MatrixEntry, RowMatrix}
import org.apache.spark.mllib.linalg.{Matrices, Matrix, Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.util.SizeEstimator

// Vectors and Matrices in Spark ML (MLLib)
object les8 {

  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_8")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext


  def main(args: Array[String]): Unit = {

    // test1
    // test2
    // test3
     test4
    // test5
    // test6
  }

  class Container (val data: Array[Int]) {
    val local = 123
    println("Container created!")
  }

  // 1. Local Vectors (dense and sparse)
  def test1 =
  {
    // Create a dense vector (1.0, 0.0, 3.0).
    val dv: Vector = Vectors.dense(1.0, 0.0, 3.0)
    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its indices and values corresponding to nonzero entries.
    val sv1: Vector = Vectors.sparse(3, Array(0, 2), Array(0.0, 3.0))
    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its nonzero entries.
    val sv2: Vector = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))

    val myArr1 = new Array[Double](10)
    val d = Vectors.dense(myArr1)
    val d2 = Vectors.dense(myArr1.clone())
    myArr1(2) = 5

    // convert to ML Vectors
    val dvML = dv.asML
    val sv1ML = sv1.asML
    val sv2ML = sv2.asML

    val aaa = 111;
  }

  // labeled points
  def test2 =
  {
    // Create a labeled point with a positive label and a dense feature vector.
    val features = Vectors.dense(1.0, 0.0, 3.0)
    val pos = LabeledPoint(1.0, features)

    // Create a labeled point with a negative label and a sparse feature vector.
    val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))

    val tmp = 123;
  }

  // Local matrices
  def test3 =
  {
    val data = Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0)

    // Create a dense matrix ((1.0, 2.0), (3.0, 4.0), (5.0, 6.0))
    val dm: Matrix = Matrices.dense(3, 2, data)
    val el20 = dm(2,0)
    val dmt = dm.transpose
    val el02 = dmt(0,2)

    // Create a sparse matrix (
    // (9.0, 1.0),
    // (0.0, 8.0),
    // (0.0, 6.0))
    val sm: Matrix = Matrices.sparse(3, 2,
      Array(0, 1, 4), Array(0, 0, 1, 2), Array(9, 1, 8, 6))

    val dm2 = Matrices.dense(sm.numRows, sm.numCols, sm.toArray)

    // remember: arrays in JVM are always zero-initialized
    val zerosI = new Array[Int](5)
    val zerosD = new Array[Double](5)

    val tmp = 123;
  }

  // RowMatrices
  def test4 = {

    val dv: Vector = Vectors.dense(1.0, 0.0, 3.0)
    val sv: Vector = Vectors.sparse(4, Array(0, 1, 3), Array(2.0, 1.0, 6.0))
    val rowsArr = Array(sv, dv)

    val sz = SizeEstimator.estimate(rowsArr)

    val rows: RDD[Vector] = sc.parallelize(rowsArr)
    val mat: RowMatrix = new RowMatrix(rows)

    // Get its size.
    val m = mat.numRows()
    val n = mat.numCols()

    // collect rows from RDD
    val rowsArr2 = mat.rows.collect()

    val aaa = 111
  }

}
