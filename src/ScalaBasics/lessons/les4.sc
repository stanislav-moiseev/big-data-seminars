/*
//------------------------------------------
// 3. generic-классы
class Queue[A] {
  private var elements: List[A] = Nil
  def push(x: A) { elements = elements :: List(x) }
  def head: Option[A] = {
    if (elements.isEmpty) None
    else Some(elements.head)
  }
  def pop: Option[A] = {
    val currentHead = head
    if (!currentHead.isEmpty) {
      elements = elements.tail
    }
    currentHead
  }
}

val que_i = new Queue[Int]
que_i.push(3)
que_i.push(1)
que_i.push(4)
que_i.push(1)
que_i.push(5)
que_i.push(9)
while (!que_i.head.isEmpty)
  println(que_i.pop.get)

trait Animal
class Lion extends Animal
class Wolf extends Animal
val que_a = new Queue[Animal]
que_a.push(new Lion)
que_a.push(new Lion)
que_a.push(new Lion)
que_a.push(new Wolf)
while (!que_a.head.isEmpty)
  println(que_a.pop.get)
*/

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

/*
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
 */

/*
//------------------------------------------
// 3. synchronized
import java.util.concurrent.{Callable, Executors, Future, TimeUnit}

object Program {

  var count = 0

  val task_o = new java.util.concurrent.Callable[Unit] {
    def call(): Unit = {
        count = count + 1
    }
  }

  val task_s = new java.util.concurrent.Callable[Unit] {
    def call(): Unit = this.synchronized{
        count = count + 1
      }
  }

  def main = {
    // создаем пул тредов и задачи
    val nthread = 100
    val ntasks = 10000

    val executor = Executors.newFixedThreadPool(nthread)
    var tasksSubmited = 0
    val tasks = Range(0, ntasks).map( i => {
      tasksSubmited = tasksSubmited + 1
      executor.submit( task_o ) } )

    System.out.println (s"tasksSubmited = $tasksSubmited ")
    executor.shutdown()
    executor.awaitTermination(5, TimeUnit.SECONDS)
    System.out.println (s"count = $count ")

  }
}

Program.main
*/


/*
//------------------------------------------
// 4. adding two arrays
import java.util.concurrent.{Callable, Executors, Future, TimeUnit}
import scala.util.Random

object Program {

  val rg = new java.util.Random()

  val add_task: java.util.concurrent.Callable[Int] = (a: Int, b: Int) => { a + b }

  def main = {

    val maxVal = 10
    val n = 5

    val ar1 = Range(0,n).map( _ => rg.nextInt( maxVal )  )
    val ar2 = Range(0,n).map( _ => rg.nextInt( maxVal )  )

    // 1. add two vectors in single thread
    val ar1
    val sum1 = ar1.zip(ar2).map( p => p._1 + p._2 )

    // 2. add two vectors by multiple threads
    val nthread = 4
    val executor = Executors.newFixedThreadPool(nthread)
    Range(0, n).map( i => {
      executor.submit( add_task(ar1[i], ) ) } )

    System.out.println (s"tasksSubmited = $tasksSubmited ")
    executor.shutdown()
    executor.awaitTermination(5, TimeUnit.SECONDS)
    System.out.println (s"count = $count ")



//    ar1.foreach( el => print( el + ",") )
//    print("\n")
//    ar2.foreach( el => print( el + ",") )
//    print("\n")
//    println("--------------------------------")
//    sum1.foreach( el => print( el + ",") )
//    print("\n")


  }
}

Program.main
*/