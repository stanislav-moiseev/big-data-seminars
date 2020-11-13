// Multithreading in Scala/Java (cont.)

/*
//------------------------------------------
// 1. add two arrays by multithread executor
import java.util.concurrent.{Callable, Executors, Future, TimeUnit}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Program {

  def profile[R](code: => R, t: Long = System.nanoTime) =
    (code, System.nanoTime - t)

  val rg = new java.util.Random()

  def main = {

    val maxVal = 10
    val k = 4  // number of threads
    val n = 1e7.toInt

    val ar1: Array[Int] = Range(0,n).map( _ => rg.nextInt( maxVal )  ).toArray
    val ar2: Array[Int] = Range(0,n).map( _ => rg.nextInt( maxVal )  ).toArray

    // 1. add two vectors in single thread
    var sum1: Array[Int] = Array.fill(n)(0)
    val (r1, t1) = profile ( {  //sum1 = ar1.zip(ar2).map( p => p._1 + p._2 )
      Range(0, n).map ( idx => { sum1(idx) = ar1(idx) + ar2(idx) } )
       })
    println(s"Single thread complete in - ${t1 * 1.0e-9} seconds ")

    // 2. add two vectors by n-tasks in k threads
    val sum2 = Array.fill(n)(0)
    class Task(val from: Int, val to: Int) extends Callable[Unit] {
      override def call = this.synchronized {
        try {
          Range(from, to).map ( idx => { sum2(idx) = ar1(idx) + ar2(idx) } )
        } catch {
          case e: InterruptedException =>
            e.printStackTrace ()
        }
      }
    }

    // определяем пул тредов и создаем задачи
    val executor = Executors.newFixedThreadPool(k)
    val (r2, t2) = profile ({
      val tasks_cnt = k
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
*/

/*
//------------------------------------------
// 2. upper type bounds
object Program {

  class Queue[A] {
    private var elements: List[A] = Nil
    def push(x: A) { elements = elements ++ List(x) }
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

  abstract class Animal {
    def name: String
  }

  abstract class Pet extends Animal

  class Cat extends Pet {
    override def name: String = "Cat"
  }

  class Dog extends Pet {
    override def name: String = "Dog"
  }

  class Lion extends Animal {
    override def name: String = "Lion"
  }

  class PetQue[P <: Pet] extends Queue[P]

  def main: Unit = {

    val dogQueue = new PetQue[Dog]
    dogQueue.push( new Dog )
    while (!dogQueue.head.isEmpty)
      println(dogQueue.pop.get.name)

    val catQueue = new PetQue[Cat]
    catQueue.push( new Cat )
    while (!catQueue.head.isEmpty)
      println(catQueue.pop.get.name)

    val petQueue = new PetQue[Pet]
    petQueue.push( new Cat )
    petQueue.push( new Dog )
    while (!petQueue.head.isEmpty)
      println(petQueue.pop.get.name)

//    val lionQueue = new PetQue[Lion]
  //  lionQueue.push( new Lion)

  }
}

Program.main
*/


//------------------------------------------
// 3. invariant/covariant inheritance
object Program {

  def main: Unit = {

    class MyContainer[+T](val elements: List[T])

    class Person( val id: Int )
    class Worker( val idP: Int, val idW: Int )
                 extends Person( idP )

    // some function
    def myFunction( pers: MyContainer[Person] ): Int = {
      // ... do smth
      pers.elements.size
    }

    val people = new MyContainer[Person](
      List( new Person(128), new Person(256)) )
    val workers = new MyContainer[Worker](
      List(new Worker(512, 101)) )

    println( s"people - myFunction result = ${myFunction(people)}" )
    println( s"workers - myFunction result = ${myFunction(workers)}" )

  }
}

Program.main


scala.collection.mutable.ArrayBuffer

//------------------------------------------
// 4. invariant/contravariant inheritance
object Program {

  def main: Unit = {

    class MyContainer[-T](val name: String)

    class Person( val id: Int )
    class Worker( val idP: Int, val idW: Int )
      extends Person( idP )

    // some function
    def myFunction( pers: MyContainer[Worker] ): Int = {
      // ... do smth
      pers.name.size
    }

    val people = new MyContainer[Person]( "Hello" )
    val workers = new MyContainer[Worker]( "Bye" )

    println( s"people - myFunction result = ${myFunction(people)}" )
    println( s"workers - myFunction result = ${myFunction(workers)}" )

  }
}

Program.main

// Notes:
//-------------------------
// covariant     - if A < B then F[A]<F[B]
// invariant     - if A < B then (F[A]!<F[B])&&(F[A]!>F[B])
// contravariant - if A < B then F[A]>F[B] 


