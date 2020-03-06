import org.apache.spark._
import org.apache.spark.rdd.RDD

object Test {
  private def getTimeStr(totalNano: Long): String = {
    val days: Long     = totalNano / (24*60*60*1000000000L)
    val hours: Long    = totalNano / (60*60*1000000000L) - (days*24)
    val mins: Long     = totalNano / (60*1000000000L)    - (days*24 + hours)*60
    val nanosecs: Long = totalNano - (((days*24 + hours)*60) + mins)*60*1000000000L
    val secs: Double   = nanosecs * 1e-9
    
    if(days > 0) {
      f"$days%2dd $hours%2dh $mins%2dm"
    } else if(hours > 0) {
      f"$hours%2dh $mins%2dm $secs%4.1fs"
    } else if(mins  > 0) {
      f"$mins%2dm $secs%4.1fs"
    } else {
      f"$secs%4.1fs"
    }
  }


  private def measureHashPartitionerTime[T](rdd: RDD[T], input: String) {
    val t0 = System.nanoTime()
    rdd
      .repartition(10*sc.defaultParallelism)
      .count
    val t1 = System.nanoTime()

    printf("HashPartitioner time: %s\n", getTimeStr(t1 - t0))
  }

  private def measureRangePartitionerTime[T](rdd: RDD[T], input: String)(implicit tagT: ClassTag[T]) {
    val t0 = System.nanoTime()
    val rdd2 = rdd.zipWithIndex.map(_.swap)
    rdd2
      .partitionBy(new RangePartitioner(10*sc.defaultParallelism, rdd2))
      .values
      .count
    val t1 = System.nanoTime()

    printf("RangePartitioner time: %s\n", getTimeStr(t1 - t0))
  }

  private def measureUniformPartitionerTime[T](rdd: RDD[T], input: String)(implicit tagT: ClassTag[T]) {
    val t0 = System.nanoTime()
    UniformPartitioner.repartition(rdd, 10*sc.defaultParallelism).count
    val t1 = System.nanoTime()

    printf("UniformPartitioner time: %s\n", getTimeStr(t1 - t0))
  }

  def main(args: Array[String]) {
    if(args.length < 1) {
      printf("Usage: ./test INPUT\n", args(0))
      System.exit(0)
    }

    val input: String  = args(0)

    val sc = SparkContext.getOrCreate()
    sc.setLogLevel("ERROR")
    
    val rdd  = sc
      .textFile(input)
      .map(line => line.length)
      .cache

    rdd.count

    measureHashPartitionerTime(rdd, input)
    measureRangePartitionerTime(rdd, input)
    measureUniformPartitionerTime(rdd, input)
  }
}
