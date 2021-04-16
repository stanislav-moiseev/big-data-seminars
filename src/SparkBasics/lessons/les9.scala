package SparkBasics.lessons

import org.apache.spark.mllib.linalg.distributed._
import org.apache.spark.mllib.linalg.{Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.linalg.distributed.{BlockMatrix, CoordinateMatrix, MatrixEntry}

// I. Vectors and Matrices (cont)
// II.
object les9 {

  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_9")
    .master("local[*]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  def main(args: Array[String]): Unit = {
    // test1
    // test2
    // test3
    test4
  }

  // IndexedRowMatrix
  def test1: Unit =
  {
    val r1: IndexedRow = new IndexedRow(0, Vectors.dense(1.0, 0.0, 3.0))
    val r2: IndexedRow = new IndexedRow( 1, Vectors.sparse(4, Array(0, 1, 3), Array(2.0, 1.0, 6.0)))
    val rowsSeq = Seq(r2, r1)

    val rows: RDD[IndexedRow] = sc.parallelize(rowsSeq)
    // Create an IndexedRowMatrix from an RDD[IndexedRow].
    val mat: IndexedRowMatrix = new IndexedRowMatrix(rows)

    // Get its size.
    val m = mat.numRows()
    val n = mat.numCols()

    // Drop its row indices.
    val rowMat: RowMatrix = mat.toRowMatrix()
    val lra = rowMat.rows.collect()
    val lira = mat.rows.collect()

    val aaa = 111
  }

  // CoordinateMatrix
  def test2: Unit = {

    val e1 = new MatrixEntry(0,0,5.0)
    val e2 = new MatrixEntry(0,1,4.0)
    val e3 = new MatrixEntry(1,2,7.0)

    val entries: RDD[MatrixEntry] = sc.parallelize( Array(e1,e2,e3) )
    val mat: CoordinateMatrix = new CoordinateMatrix(entries)

    // Get its size.
    val m = mat.numRows()
    val n = mat.numCols()

    // Convert it to an RowMatrix whose rows are sparse vectors.
    val rowMatrix = mat.toRowMatrix()
    val rows = rowMatrix.rows.collect()

    val aaa = 111
  }

  // BlockMatrix
  def test3: Unit = {

    val locEntries = List(
      MatrixEntry(0,0,1.0),MatrixEntry(0,1,2.0),
      MatrixEntry(1,0,3.0),MatrixEntry(1,1,4.0),
      MatrixEntry(2,2,1.0),MatrixEntry(2,3,2.0),
      MatrixEntry(3,2,3.0),MatrixEntry(3,3,4.0) )

    val entries: RDD[MatrixEntry] = sc.parallelize(locEntries)
    // Create a CoordinateMatrix from an RDD[MatrixEntry].
    val coordMat: CoordinateMatrix = new CoordinateMatrix(entries)
    // Transform the CoordinateMatrix to a BlockMatrix
    val matA: BlockMatrix = coordMat.toBlockMatrix(2,2).cache()
    // Validate whether the BlockMatrix is set up properly. Throws an Exception when it is not valid.
    // Nothing happens if it is valid.
    matA.validate()

    val blocks = matA.blocks.collect()

    // Calculate A^T * A.
    val ata = matA.transpose.multiply(matA)
    val ataBlocks = ata.blocks.collect()

    val dbg = 111
  }

  // joining of RDDs
  def test4 = {
    // RDD1 - K,V
    val codeRowsCnt = sc.parallelize(Seq(
      ("Ivan", 240),
      ("Ivan", 50),
      ("Andrey", 50),
      ("Petr", 39),
      ("Elena", 290),
      ("Katya", 300))).groupByKey()

    // RDD2 - K,V
    val programmerLangs = sc.parallelize(Seq(
      ("Ivan", "Java"),
      ("Elena", "Scala"),
      ("Elena", "Pascal"),
      ("Petr", "Scala"),
      ("Mary", "C++"))).groupByKey()

    println("case 1: join (inner join)")
    println("----------------------------")
    codeRowsCnt
      .join(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 2: left outer join")
    println("----------------------------")
    codeRowsCnt
      .leftOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 3: right outer join")
    println("----------------------------")
    codeRowsCnt
      .rightOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()

    println("case 4: full outer join")
    println("----------------------------")
    codeRowsCnt
      .fullOuterJoin(programmerLangs)
      .sortByKey()
      .collect()
      .foreach(println(_))
    println()
  }

}
