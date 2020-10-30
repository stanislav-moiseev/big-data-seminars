// Многопоточность в Scala

//------------------------------------------
// 1. extend Runnable interface
/*
// new class
class Task (val taskId: Int) extends Runnable  {
  override def run(): Unit = {
    val threadId = Thread.currentThread().getId
    System.out.println(s"   - task $taskId started in thread $threadId ...")
    Thread.sleep(1000)
    System.out.println(s"   - task $taskId finished ...")
  }
}

object Program {
  def main(): Unit = {

    System.out.println("main thread started ...")

    val task_1 = new Task(123)
    task_1.run()

    val thread_1 = new Thread( task_1 )
    thread_1.start()

    thread_1.join()

    System.out.println("main thread finished ...")
  }
}

// run the program
Program.main()
 */

//------------------------------------------
// 2. ExecutorService
import java.util.concurrent.{Callable, Executors, Future, TimeUnit}
import scala.collection.mutable

// создаем таск
class Task(val taskId: Int) extends Callable[ (Int, Int) ] {
  override def call: (Int, Int) = {
    try {
      val trId = Thread.currentThread.getId
      System.out.println (s"task - $taskId - started in thread - $trId")
      Thread.sleep(1000)
      System.out.println (s"task - $taskId - finished")
      return (taskId, taskId + 1000)
    } catch {
      case e: InterruptedException => System.out.println (s"task - $taskId - interrupted ")
      e.printStackTrace ()
      return (taskId, 1)
    }
  }
}

object Program {
  def main = {
    // создаем пул тредов и задачи
    val nthread = 4
    val ntasks = 15

    val executor = Executors.newFixedThreadPool(nthread)
    val tasks = Range(0, ntasks).map(id => new Task(id))
    val results = tasks.map ( t => executor.submit(t) )

    System.out.println ("//-------------------")
    results.foreach( el => System.out.println (s"task - ${el.get()._1} - result - ${{el.get()._2}} ") )

    executor.shutdown()
  }
}

Program.main