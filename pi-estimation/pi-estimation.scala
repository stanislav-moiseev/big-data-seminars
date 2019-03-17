import scala.math._

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
def spark_pi(num_points: Int) {
  /* Create a Spark RDD programmatically. */
  val num_partitions = 1000
  val tasks          = 0 until num_points
  val tasksRDD       = sc.parallelize(tasks, num_partitions)

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
