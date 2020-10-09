package ScalaBasics

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

  def main(args: Array[String]): Unit = {
    import Conversions2._

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
