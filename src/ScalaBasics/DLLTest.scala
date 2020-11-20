//package ScalaBasics
//
//import com.sun.jna._
//
//object DLLTest {
//
//  trait IMyFuncs extends Library {
//    def my_init()
//
//    def my_next(a: Int): Int
//  }
//
//  def main(args: Array[String]): Unit = {
//
//    val mathFuncs: IMyFuncs = Native.loadLibrary("C:\\jnadll\\my_d1ll.dll", classOf[IMyFuncs]).asInstanceOf[IMyFuncs]
//    mathFuncs.my_init()
//
//    val a = 10
//    for (i <- 1 to 10) {
//      val res = mathFuncs.my_next(a)
//      System.out.println(Integer.toString(res))
//    }
//  }
//
//}
