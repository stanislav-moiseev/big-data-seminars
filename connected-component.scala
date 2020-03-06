import org.apache.spark.rdd._
import scala.math._

type Node = Int

/* 'filename'   Name of file or directory with graph edges.  In case of a
 *              directory, all files from that directory will be loaded.
 *
 * 'start_node' An identifier of a graph node which we will find the connected
 *              component for.
 */
def ccomp(filename: String, start_node: Node) {

  /* Define an RDD containing all graph edges in a form that supports efficient
   * look-ups of all neighboring graph nodes for a given graph nodes.
   * 
   * Conceptually, 'edges_rdd' is a sequence of pairs '(u, neighbors_u)' where
   * 'neighbors_u' is the list of all nodes 'v' such that there exists an
   * undirected edge '(u, v)'.
   */
  val edges_rdd: RDD[(Node, Iterable[Node])] = sc
    /* Define an RDD from a text file.  The RDD will represent a sequence of all
     * lines of text file. */
    .textFile(filename)

    /* Parse every text line.  The resulting RDD will represent a sequence of all
     * graph edges. */
    .flatMap(line => {
               val ints = line.split(" ").map(_.toInt)
               /* Treat all edges as undirected and insert them twice. */
               List((ints(0), ints(1)), (ints(1), ints(0)))
    })

    /* Group the values for each key into a single sequence. 
     * E.g., this will transform a sequence of edges
     *   [(1,2), (2,1), (1,3), (1,4), (2,5)]
     * into
     *   [(1, [2,3,4]), (2, [1,5])] */
    .groupByKey

  /* The list of all nodes in the connected component for 'start_node'. */
  var ccomp: List[Node]  = List()
  /* The list of "new" nodes. */
  var border: List[Node] = List(start_node)

  while(!border.isEmpty) {

    printf("%s\n", border.foldLeft("")((s, x) => s ++ x.toString ++ " "))

    /* neighbors = ⋃_{u ∈ border} {v: (u,v) is an edge in G} */
    var neighbors: List[Node] = List()

    for(u <- border) {
      /* Get the list of all neighbors of 'u'.  This operation will be done
       * efficiently by only searching the partition that the key 'u' maps to.
       * 
       * neighbors_u = {v: (u,v) is an edge in G}
       * 
       * The function 'flatten()' concatenates a sequence of sequences into
       * sequence.
       */
      val neighbors_u = edges_rdd.lookup(u).flatten

      neighbors = neighbors ++ neighbors_u
    }

    ccomp = ccomp ++ border
    border = neighbors.distinct.filter(u => !ccomp.exists(_ == u))
  }
}

