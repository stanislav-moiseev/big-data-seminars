package ScalaBasics

import org.apache.commons.math3.distribution.PoissonDistribution
import org.apache.commons.math3.distribution.UniformIntegerDistribution
import org.apache.commons.math3.distribution.BinomialDistribution
import org.apache.spark.util.SizeEstimator._

object VariousTests {


  def main(args: Array[String]): Unit = {

    //test_log()

    //testClassConstructors()
    //test_equals()

    // test different distributions
    //test_Uniform()
    //test_Poisson()
    //test_Bernoulli()
    testInstOrder

  }

  def fastlog2(x: Float): Float = {
    val a = -0.6296735F
    val b = 1.466967F;
    val ux1_i = java.lang.Float.floatToRawIntBits(x);
    val exp = (ux1_i & 0x7F800000) >> 23;
    val greater = ((ux1_i & 0x00400000.toInt) != 0)
    if (greater) {
      val ux2_i = (ux1_i & 0x007FFFFF) | 0x3f000000;
      val signif = java.lang.Float.intBitsToFloat(ux2_i) - 1.0F;
      val fexp = exp - 126;
      return (fexp + signif * (a * signif + b))
    } else {
      val ux2_i = (ux1_i & 0x007FFFFF) | 0x3f800000;
      val signif = java.lang.Float.intBitsToFloat(ux2_i);
      val fexp = exp - 127;
      return (fexp + signif * (a * signif + b))
    }
  }

  def test_log(): Unit = {

    val x: Float = (0.98).toFloat

    val l1 = Math.log(x) / Math.log(2.0)
    val l2 = fastlog2(x)

    val aaa = 111;

  }

  def testClassConstructors(): Unit = {

    // объявляем класс прямо внутри метода
    class MyClass(private var Name: String, private var age: Int, val Name2: String) {
      println("Constructor begins")

      private var field_1 = 1.0;
      var field_2 = 3.0;

      def printName(): Unit = println(s"Name = $Name")

      def printFileds(): Unit = println(s"filed_1 = $field_1 | filed_2 = $field_2")

      // другой конструктор
      def this(age: Int) = this("", age, "no_name_2")

      println("Constructor ends")
    }

    // создаем объект класса разными конструкторами
    val mc = new MyClass("Andrey", 36, "12345");
    val anomymous = new MyClass(135);

    mc.printName()
    mc.printFileds()
    val ccc = 111

  }

  // тест сравнения классов
  def test_equals(): Unit = {

    class MyCl(val name: String, val age: Int, val data: Array[Int]) {
      def apply(idx: Int): Int = data(idx)

      def update(idx: Int, new_val: Int) = {
        data(idx) = new_val
      }

      def equals(other: MyCl): Boolean = {
        (this.name == other.name) &&
          (this.age == other.age) &&
          (this.data.sameElements(other.data))
      }

      def ==(other: MyCl): Boolean = {
        this.equals(other)
      }
    }

    //val seq = Array(1,2,3,4,5)
    val t1 = new MyCl("Andrey", 36, Array(1, 2, 3, 4, 5))
    val t2 = new MyCl("Andrey", 36, Array(1, 2, 3, 4, 5))
    val t3 = new MyCl("Anna", 35, Array(1, 2, 3, 4, 5))

    val c1 = t1
    val r1 = (t1 == t2)
    val r2 = (t1 == c1)
    val r3 = (t1.equals(t2))
    val r4 = (t1 == t3)

    t1(2) = 5 // update
    val a = t1(1) // apply

    val aaa = 111;
  }

  def test_Uniform(): Unit = {

    class Elem(val name: String, val value: Integer)
    case class cElem(val name: String, val value: Integer)
    val rng = new UniformIntegerDistribution(1, 10)
    val elements = (1 to 10).map(num => new Elem(num.toString, rng.sample()))

    var a = new Elem("Privet", 1)
    var b = cElem("Privet", 1)

    val sa = estimate(a)
    val sb = estimate(b)

    val sum_of_odd_vals = elements.foldLeft(0)((odd_cnt, e) => odd_cnt + (e.value % 2))
    val aaa = 111

  }

  // тест распределения Пуассона
  // subsampling (c повторениями)
  def test_Poisson(): Unit = {

    val num_rows = 100000

    //val data = (1 to num_rows)

    val lambda = 0.8
    val seed = 0
    val poisson = new PoissonDistribution(lambda)
    poisson.reseedRandomGenerator(seed + 1)

    val subsampleWeights = (1 to num_rows).map(num => poisson.sample())
    val mean = subsampleWeights.sum.toDouble / num_rows.toDouble

    // считаем вероятности выпадения дискретных величин
    val bins = (0 to 9).map(r_value => subsampleWeights.count(_ == r_value))
    val probs = bins.map(cnt => cnt.toDouble / num_rows.toDouble)

    val aaa = 111;
  }

  // subsampling (без повторений)
  def test_Bernoulli(): Unit = {

    //val data = (1 to 1000000)

    val p = 0.1
    val seed = 1
    val numBits = 1000000
    val bern = new BinomialDistribution(1, p)
    bern.reseedRandomGenerator(seed)

    val bits = (1 to numBits).map(num => bern.sample())
    val prob = bits.sum.toDouble / numBits.toDouble

    val aaa = 111
  }

}
