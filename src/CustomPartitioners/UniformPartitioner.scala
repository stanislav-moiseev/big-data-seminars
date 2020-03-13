/*******************************************************************************
 * This module implements a simple custom RDD partitioner and a function that
 * repartitions an RDD into partitions of roughly equal size.
 * 
 * @author      Moiseev Stanislav
 * @date        March 2020
 * *****************************************************************************/

package CustomPartitioners

import scala.reflect.ClassTag

import org.apache.spark.Partitioner
import org.apache.spark.rdd.RDD

object UniformPartitioner extends Serializable {

  /* This function repartitions and shuffles an RDD.  A new RDD will contain
   * exactly 'numPartitions' partitions, all of roughly equal sizes (with a
   * maximal size difference of 1).
   * 
   * This function preserves the order of elements in an RDD.
   * 
   * The result of this function is similar to that produced by the standard
   * Spark 'RangePartitioner' ('org.apache.spark.RangePartitioner').  A notable
   * difference is that, unlike 'RangePartitioner' this functions guarantees the
   * number of partitions in the resulting RDD; this function is also faster.
   * 
   * @param rdd                 An RDD to be repartitioned.
   * @param numPartitions       The number of partitions in the resulting RDD. 
   */
  def repartition[T](rdd: RDD[T], numPartitions: Int)(implicit tagT: ClassTag[T]): RDD[T] = {
    require(numPartitions > 0, s"The number of partitions ($numPartitions) must be positive.")

    /* 'partLengths(k)' contains the number of elements in the k-th partition of the
     * original RDD. */
    val partLengths: Array[Long] = rdd.mapPartitions(elems => Some(elems.length.toLong).iterator).collect
    val totalLength: Long        = partLengths.sum
    val indices: Array[Long]     = partLengths.scanLeft(0:Long)(_ + _)

    /* rdd2 = [(0,a0), (1,a1), (2,a2), ...] */
    val rdd2: RDD[(Long, T)] = rdd
      .mapPartitionsWithIndex({case (k, elems) => (indices(k) until indices(k+1)).iterator zip elems})

    rdd2
      .partitionBy(new UniformPartitioner(totalLength, numPartitions))
      .values
      .setName(s"${rdd.name} (uniformly partitioned)")
  }

  /* A simple partitioner that partitions all elements of an RDD into partitions
   * of roughly equal sizes (with maximal size difference of '1', which will
   * occur when 'numElements % partitions != 0').
   * 
   * @note This class was designed for speed-efficiency.  It assumes several
   *       constraints (namely, 'numPartitions > 0', key type is 'Long', and all
   *       keys are non-negative).
   */
  private final class UniformPartitioner(numElements: Long, partitions: Int) extends Partitioner {
    def numPartitions: Int            = partitions

    def getPartition(key: Any): Int   = {
      val p: Int = (key.asInstanceOf[Long] / partitionSize).toInt
      /* Protect from floating-point precision errors which will occur when
       * '1.0 / numElements' is very small (though, for 'Double', this is
       * unlikely to occur in practice). */
      if(p >= numPartitions) (numPartitions - 1) else p
    }

    private val partitionSize: Double = numElements.toDouble / partitions
  }
}

