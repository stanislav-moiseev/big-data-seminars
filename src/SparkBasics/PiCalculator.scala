package SparkBasics

import org.apache.spark.sql.SparkSession
import math.random

object PiCalculator {

  /* Single-machine version of the Pi estimation algorithm. */
  def local_pi(num_points: Int) {
    var count = 0

    for (k <- 0 until num_points) {
      /* Get a random point in square [-1..1] x [-1..1]. */
      val x = 2 * random - 1
      val y = 2 * random - 1

      /* Count the number of points inside the circle with center (0, 0) and radius 1. */
      if (x*x + y*y < 1.0) { count += 1 }
    }

    printf("π ≈ %f\n", 4.0 * count / num_points)
  }

  /* Spark version of the Pi estimation algorithm. */
  def spark_pi(num_points: Int, spark: SparkSession ) {

    /* Create a new RDD[Long] containing elements from 0 to 'num_points'
     * (exclusive), increased by step 1. */
    val tasksRDD = spark.sparkContext.range(0, num_points, 1, 100);

    /* The number of points placed inside the circle of radius 1. */
    val count =
      tasksRDD
        .map ({k =>
          /* Get a random point in square [-1..1] x [-1..1]. */
          val x = 2 * random - 1
          val y = 2 * random - 1

              /* Test if the point is inside the circle with center (0, 0) and radius 1. */
              if (x*x + y*y < 1.0) 1 else 0
            })
      .reduce(_ + _)

    printf("π ≈ %f\n", 4.0 * count / num_points)
  }


}
