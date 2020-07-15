package ScalaBasics

import org.apache.commons.math3.distribution.PoissonDistribution

object VariousTests {


  def main(args: Array[String]): Unit = {

    test_log()

    //testClassConstructors()
    // testPoisson ()
    //test_equals()
  }

  def fastlog2( x : Float): Float = {
    val a = -0.6296735F
    val  b =  1.466967F;
    val ux1_i = java.lang.Float.floatToRawIntBits(x);
    val exp = (ux1_i & 0x7F800000) >> 23;
    val greater = ((ux1_i & 0x00400000.toInt) != 0)
    if (greater) {
      val ux2_i = (ux1_i & 0x007FFFFF) | 0x3f000000;
      val signif = java.lang.Float.intBitsToFloat(ux2_i) - 1.0F;
      val fexp = exp - 126;
      return ( fexp + signif*(a*signif + b) )
    } else {
      val ux2_i = (ux1_i & 0x007FFFFF) | 0x3f800000;
      val signif = java.lang.Float.intBitsToFloat(ux2_i);
      val fexp = exp - 127;
      return ( fexp + signif*(a*signif + b) )
    }
  }

  def test_log (): Unit = {

    val x : Float = (0.98).toFloat

    val l1 = Math.log( x ) / Math.log( 2.0 )
    val l2 = fastlog2( x )

    val aaa = 111;

  }

  def testClassConstructors (): Unit = {

    // объявляем класс прямо внутри метода
    class MyClass (var Name:String, var age:Int) {
      println("Constructor begins")

      var field_1 = 1.0;
      var field_2 = 3.0;

      def printName(): Unit = println(s"Name = $Name")
      def printFileds(): Unit = println(s"filed_1 = $field_1 | filed_2 = $field_2")

      // другой конструктор
      def this(age:Int) = this("",age)

      println("Constructor ends")
    }

    // создаем объект класса разными конструкторами
    val mc = new MyClass("Andrey", 36);
    val anomymous = new MyClass( 135);

    mc.printName()
    mc.printFileds()
    val ccc = 111

  }

  // тест распределения Пуассона
  def testPoisson () : Unit = {

    val subsample = 1.0
    val seed  = 0
    val numSubsamples = 1000000

    val poisson = new PoissonDistribution (subsample)
    poisson.reseedRandomGenerator (seed + 1)

    val subsampleWeights = new Array[Int] (numSubsamples)
    var subsampleIndex = 0
    while (subsampleIndex < numSubsamples) {
      subsampleWeights (subsampleIndex) = poisson.sample ()
      subsampleIndex += 1
    }

    // считаем вероятности выпадения дискретных величин
    val p_0 = subsampleWeights.count( _ == 0 ) / numSubsamples.toDouble
    val p_1 = subsampleWeights.count( _ == 1 ) / numSubsamples.toDouble
    val p_2 = subsampleWeights.count( _ == 2 ) / numSubsamples.toDouble
    val p_3 = subsampleWeights.count( _ == 3 ) / numSubsamples.toDouble
    val p_4 = subsampleWeights.count( _ == 4 ) / numSubsamples.toDouble
    val p_5 = subsampleWeights.count( _ == 5 ) / numSubsamples.toDouble
    val p_6 = subsampleWeights.count( _ == 6 ) / numSubsamples.toDouble

    val total = subsampleWeights.sum

    val aaa = 111;

  }

//  // тест параметризованных функций
//  def test_generics () : Unit = {
//
//    def add[T] (p1:T, p2:T):T = {
//       println( s"Parameters are: $p1" )
//    }
//
//
//  }

  //  // тест параметризованных функций
  //  def test_generics () : Unit = {

   // тест сравнения классов
   def test_equals () : Unit = {

     class MyCl (val name: String, val age:Int, val data: Array[Int]) {
       def apply(idx: Int): Int = data(idx)
       def update(idx: Int, new_val: Int)  = {data(idx) = new_val}
       def equals( other: MyCl ) : Boolean = {
           (this.name == other.name) &&
           (this.age == other.age) &&
           (this.data.sameElements( other.data ) )
       }
       def ==( other: MyCl ) : Boolean = {
           this.equals( other )
       }
     }

     //val seq = Array(1,2,3,4,5)
     val t1 = new MyCl("Andrey", 36, Array(1,2,3,4,5)  )
     val t2 = new MyCl("Andrey", 36, Array(1,2,3,4,5) )
     val t3 = new MyCl("Anna", 35,  Array(1,2,3,4,5) )

     val c1 = t1
     val r1 = (t1 == t2)
     val r2 = (t1 == c1)
     val r3 = (t1.equals(t2))
     val r4 = (t1 == t3)

     t1(2) = 5     // update
     val a = t1(1) // apply

     val aaa = 111;
   }





}
