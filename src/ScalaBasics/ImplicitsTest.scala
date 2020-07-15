package ScalaBasics

object ImplicitsTest {

  class MyInteger( val i:Int ) {
    def print = {
      println(s"print from implicit method - $i")
    }
  }

  object Conversions{
    implicit def int2MyInt2(i:Int) = new MyInteger(i)
    implicit def myInt2Int( mI:MyInteger) = mI.i
  }

  def print( i: Int ) = {
    println(s"print from regular method - $i")
  }

  def main(args: Array[String]): Unit = {

    import Conversions._

    println("Start")

    1.print
    7.print

     print(8)
     print(9)

    val b = new MyInteger(3)
    val a: Int = b
  }

}
