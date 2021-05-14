package SparkBasics.lessons

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.util.Random

// GraphX
object les11 {

  // 1. create spark session
  val spark = SparkSession
    .builder()
    .appName("spark_basics_11")
    .master("local[*]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  val sc = spark.sparkContext

  // Key-Valued RDD Operations
  def main(args: Array[String]): Unit = {
    test1
    //test2
    //test3
  }

  // 1. basic graph operations
  def test1 = {
    // Create an RDD for the vertices
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Seq((3L, ("Kolya", "student")), (7L, ("Vasya", "postdoc")),
        (5L, ("Peter", "prof")), (2L, ("Anna", "prof" )),
        (10L, ("Andrey", "postdoc"))))

    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Seq(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "friend"), Edge(2L,10L, "friend")))

    // Define a default user in case there are relationship with missing user
    //val defaultUser = ("none", "none")

    // Build Graph
    val graph = Graph(users, relationships)

    //
    val edCnt1 = graph.ops.numEdges
    val edCnt2 = graph.ops.numEdges
    val vertexCnt1 = graph.ops.numVertices

    val localTriplets = graph.triplets.collect()

    // Count all users which are postdocs
    val cnt1= graph.vertices.filter { case (id, (name, pos)) => pos == "postdoc" }.count
    println(s"postdocs cnt = ${cnt1}")

    // Count all the edges where src > dst
    val cnt2 = graph.edges.filter(e => e.srcId > e.dstId).count
    println(s"incr edge cnt = ${cnt2}")

    val newVertexRDD = graph.vertices.mapValues( el => el._1.toUpperCase )
    // Build New Graph
    val graph1 = Graph(newVertexRDD, relationships)
    val triplets1 = graph1.triplets.collect()

    val a = 123
    }

}
