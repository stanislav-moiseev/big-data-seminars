package ScalaBasics

import System.nanoTime

import com.github.fommil.netlib.BLAS

object VectorsTest {

  //---------------------------------------------------------------------------
  def profile[R](code: => R, t: Long = nanoTime) = (code, nanoTime - t)

  //---------------------------------------------------------------------------
  def main(args: Array[String]): Unit = {

    val size = 1000000
    val test_cnt = 100

    // тестируем массивы
    val res1 = test_arrays( size , test_cnt)
    println(s" Array test - $res1 - ms")

    //val res2 = test_breeze_arrays( size , test_cnt )
    //println(s" breeze.DenseVector test - $res2 - ms")

  }

  //---------------------------------------------------------------------------
  def test_arrays(size: Int, test_cnt:Int): Double = {
    val rg = scala.util.Random
    var t_idx=0;
    var sum = 0.0;
    while (t_idx<test_cnt) {
      val a: Array[Int] = Array.fill[Int](size)(0)
      val b: Array[Int] = Array.fill[Int](size)(0)

      // fill array with rand values
      var i=0;
      while (i<size) {
        a(i) = rg.nextInt
        b(i) = rg.nextInt
        i += 1;
      }

      // measure elementwise incrementation
      val res = profile( { var j=0; while (j<size) { a(j)+= b(j); j+=1 } } )
      sum += res._2.toDouble;
      t_idx += 1;
    }
    sum = sum / test_cnt.toDouble;
    return sum *1.0e-6;
  }

  //---------------------------------------------------------------------------
  def test_breeze_arrays(size: Int, test_cnt:Int): Double = {
    val rg = scala.util.Random
    var t_idx=0;
    var sum = 0.0;
    while (t_idx<test_cnt) {
      var a = breeze.linalg.DenseVector.fill(size)(0)
      var b = breeze.linalg.DenseVector.fill(size)(0)

      // fill array with rand values
      var i=0;
      while (i<size) {
        a(i) = rg.nextInt
        b(i) = rg.nextInt
        i += 1;
      }

      // measure elementwise incrementation
      i = 0;
      val res = profile( while (i<size) { a(i)+= b(i); i+=1 } )
      sum += res._2.toDouble;
      t_idx += 1;
    }
    sum = sum / test_cnt.toDouble;
    return sum *1.0e-6;
  }

}
