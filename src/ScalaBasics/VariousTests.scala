package ScalaBasics

import org.apache.commons.math3.distribution.PoissonDistribution

object VariousTests {


  def main(args: Array[String]): Unit = {

    testClassConstructors()
    // testPoisson ()
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



}
