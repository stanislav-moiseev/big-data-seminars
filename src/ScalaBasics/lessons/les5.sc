//------------------------------------------
// 1. add two arrays by multithread executor
import java.util.concurrent.{Callable, Executors, Future, TimeUnit}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Program {

  def profile[R](code: => R, t: Long = System.nanoTime) = (code, System.nanoTime - t)

  val rg = new java.util.Random()

  def main = {

    val maxVal = 10
    val k = 4  // number of threads
    val n = 10e6.toInt

    val ar1: Array[Int] = Range(0,n).map( _ => rg.nextInt( maxVal )  ).toArray
    val ar2: Array[Int] = Range(0,n).map( _ => rg.nextInt( maxVal )  ).toArray

    // 1. add two vectors in single thread

    var sum1: Array[Int] = null
    val (r1, t1) = profile ( sum1 = ar1.zip(ar2).map( p => p._1 + p._2 ) )
    println(s"Single thread complete in - ${t1 * 1.0e-9} seconds ")

    // 2. add two vectors by n-tasks in k threads
    val sum2 = Array.fill(n)(0)
    class Task(val from: Int, val to: Int) extends Callable[Unit] {
      override def call = this.synchronized {
        try {
          Range(from, to).map ( idx => sum2(idx) = ar1(idx) + ar2(idx) )
        } catch {
          case e: InterruptedException =>
            e.printStackTrace ()
        }
      }
    }

    // определяем пул тредов и создаем задачи
    val executor = Executors.newFixedThreadPool(k)
    val (r2, t2) = profile ({
      //val tasks_cnt = (0.5*n).toInt
      val tasks_cnt = 4
      val len = math.floor( n / tasks_cnt ).toInt
      for ( task_idx <- Range(0,tasks_cnt) ) {
        if (task_idx < (tasks_cnt-1))
          executor.submit(new Task( task_idx*len, (task_idx+1)*len ))
        else
          executor.submit(new Task( task_idx*len, n ))
      }
      executor.shutdown()
      executor.awaitTermination(Long.MaxValue, TimeUnit.NANOSECONDS)
    } )
    println(s"Multi  thread complete in - ${t2 * 1.0e-9} seconds ")

    // сравниваем результаты
    if (sum1.zip(sum2).filter( el => el._1 != el._2 ).size != 0)
      println("Comparison - FAIL")
    else
      println( "Comparison - OK" )

  }
}

Program.main
