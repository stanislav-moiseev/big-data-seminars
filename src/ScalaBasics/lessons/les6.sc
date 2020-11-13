// 1. abstract type members
/*
object Program {

  def main = {

    trait Buffer {
      type ContType
      val elements: ContType
    }

    abstract class SeqBuffer extends Buffer {
      type ElemType
      type ContType <: Seq[ElemType]
      def length = this.elements.length
    }

    abstract class IntSeqBuffer extends SeqBuffer {
      type ElemType = Int
    }

    def newIntSeqBuf(count: Int, elem: Int): IntSeqBuffer =
      new IntSeqBuffer {
        type ContType = List[ElemType]
        val elements = List.fill(count)(elem)
      }

    val buf = newIntSeqBuf(10, 0)
    println("length = " + buf.length)
    println("content = " + buf.elements)
  }
}

Program.main
*/

/*
// 2. same with type parameters
object Program {

  def main = {

    abstract class Buffer[ContType] {
      val elements: ContType
    }
    abstract class SeqBuffer[ElemType, ContType <: Seq[ElemType]]
      extends Buffer[ContType] {
      def length = elements.length
    }

    def getMySeqBuf(size: Int, elem: Double) =
      new SeqBuffer[Double, List[Double] ] {
        val elements = List.fill(size)(elem)
      }

    val buf = getMySeqBuf(7, 8.2)
    println("length = " + buf.length)
    println("content = " + buf.elements)
  }
}

Program.main
*/
 /*
// 3. traits and mixins
object Program {
  def main(): Unit = {

    trait A {
      val message: String
    }
    class B extends A {
      val message = "I'm an instance of class B"
    }
    trait C extends A {
      def getLoudMessage = message.toUpperCase()
    }
    class D extends B with C

    val d = new D
    println(d.message)
    println(d.getLoudMessage)
  }
}

Program.main
*/

/*
// 3. traits and mixins (cont)
object Program {
  def main(): Unit = {

    abstract class AbsIterator[T] {
      def hasNext: Boolean
      def next(): T
    }

    class StringIterator(s: String) extends AbsIterator[Char] {
      private var i = 0
      def hasNext = i < s.length
      def next() = {
        val ch = s.charAt(i)
        i = i + 1
        ch
      }
    }

    trait RichIterator[T] extends AbsIterator[T] {
      def foreach(f: T => Unit): Unit = while (hasNext) f(next())
    }

    class RichStringIter(s: String) extends StringIterator(s) with RichIterator[Char]
    val myIter = new RichStringIter("Vasya")
    myIter.foreach(println)

  }
}

Program.main
*/

/*
// 4. compound types
object Program {
  def main(): Unit = {

    trait Cloneable extends java.lang.Cloneable {
      override def clone(): Cloneable = {
        println("clone - called")
        super.clone().asInstanceOf[Cloneable]
      }
    }
    trait Resetable {
      def reset: Unit
    }

    def cloneAndReset(obj: Cloneable with Resetable): Cloneable = {
      val cloned = obj.clone()
      obj.reset
      cloned
    }

    class MyClass( var name: String ) extends Cloneable with Resetable {
      def reset = name = "anonymous"
      def apply(i: Int): Char = this.name.charAt(i)
    }

    val a = new MyClass("Vasya")
    println(s"Letter with idx 2 of ${a.name} is - ${a(2)}")
    println(s"a's name before cloneAndReset is - ${a.name}")

    val b = cloneAndReset(a)
    println(s"a's name after cloneAndReset is - ${a.name}")
    println(s"b's name after cloneAndReset is - ${b.asInstanceOf[MyClass].name}")
  }
}

Program.main
*/

/*
// 5. implicit parameters

object ImplicitTest {

  case class Color(value: String)
  case class DrawingDevice(value: String)

  def write(text: String)(implicit c: Color, d: DrawingDevice) =
    s"""Writing "$text" in ${c.value} color by ${d.value}."""

  def main1 = {
    implicit val cl: Color = Color("red")
    implicit val drawingDevice: DrawingDevice = DrawingDevice("pen")
    write("A good day")
  }

  def main2 = {
    implicit val cl: Color = Color("green")
    implicit val drawingDevice: DrawingDevice = DrawingDevice("pencil")
    write("A good day")
  }

//  def main3 = {
//    implicit val cl1: Color = Color("green")
//    implicit val cl2: Color = Color("black")
//    implicit val drawingDevice: DrawingDevice = DrawingDevice("pencil")
//    write("A good day")
//  }

}

ImplicitTest.main1
ImplicitTest.main2
//ImplicitTest.main3
*/

/*
// 6. Implicit conversions
object ImplicitsTest {
  class MyInteger( val i:Int ) {
    def print = {
      println(s"MI1 - print from implicit method - $i")
    }
  }

  class MyInteger2( val i:Int ) {
    def print = {
      println(s"MI2 - print from implicit method - $i")
    }
  }

  object Conversions1{
    implicit def int2MyInt(i:Int) = new MyInteger(i)
    implicit def myInt2Int( mI:MyInteger) = mI.i
  }
  object Conversions2{
    implicit def int2MyInt2(i:Int) = new MyInteger2(i)
    implicit def myInt2Int( mI:MyInteger2) = mI.i
  }

  def print( i: Int ) = {
    println(s"print from regular method - $i")
  }

  def main(): Unit = {
    import Conversions1._

    println("Start")

    val a1 = 3
    a1.print

    1.print
    7.print

    print(8)
    print(9)

    //val b = new MyInteger2(3)
    //val a: Int = b
  }

}
ImplicitsTest.main()

 
 */