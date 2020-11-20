package ScalaBasics

import scala.util.Random
//import ScalaBasics.FastArray
import scala.collection.mutable.TreeSet

object CollectionTest {

  def main(args: Array[String]): Unit = {

    //test_1_views()
    //test_2_type_spec()
    //test_3
    //test_4()
    // test_5()
    //test_6()
    test_7()

  }

  def test_1_views(): Unit = {
    // create list with given min, max, step
    val my_list_1 = List.range(0, 10, 2)
    val tr_res_1 = my_list_1.map(a => 2 * a)

    // create list and view
    val my_list_2 = List.range(0, 10)
    val v1 = my_list_2.view
    val v2 = v1.map(a => 2 * a)
    val v3 = v2.map(a => a + 1).force
    v3.foreach(el => println(s"$el"))

  }

  // example how to create parametrized collections
  def test_2_type_spec(): Unit = {

    val data = new Array[String](10)
    for (i <- 0 to data.length - 1)
      data(i) = Random.nextInt().toString

    val data2 = new Array[Int](10)
    for (i <- 0 to data2.length - 1)
      data2(i) = Random.nextInt()

    val aaa = 111
  }

  // concatenation of lists
  def test_3(): Unit = {

    val l1 = List(1, 2, 3)
    val l2 = List(4, 5)

    val l3 = l1 ++ l2

    val l3_c = l3.foldLeft(0)((acc, el) => acc + el)

    val aaa = 111
  }

  // tests with sets
  def test_4(): Unit = {

    val set_m = scala.collection.mutable.HashMap((1, "Batman"), (2, "Superman"), (3, "Spiderman"))
    set_m.+=((5, "Daredevil"))

    val a = 111


  }

//  // test with arrays
//  def test_5(): Unit = {
//
//    val a1 = new FastArray(15)
//    a1.add(-1, 1)
//    a1.add(0, 1)
//    a1.add(15, 1)
//    a1.add(5, 1)
//
//    val a2 = new FastArray(15)
//    a2.add(0, 1)
//    a2.add(1, 1)
//    a2.add(2, 1)
//
//    a1.add(a2)
//
//    val t1 = a1.slice(0, 4)
//    val t2 = a1.data.view(0, 4).toArray
//
//    val p1 = t1.clone()
//    t1.set(0, 100)
//    p1.set(3, 33)
//
//    val aaa = 111
//
//  }

  // test of maps
  def test_6(): Unit = {

    // immutable map
    val weights = scala.collection.immutable.Map("Andrey" -> 90, "Anna" -> 55, "Misha" -> 25)
    val w1 = weights("Misha")
    val w2 = weights("Anna")
    val w3 = weights.get("Vasya")
    val weights_uc = weights.map(el => (el._1.toUpperCase, el._2))

    // mutable map
    val heights = scala.collection.mutable.Map[String, Int]()
    heights.put("Misha", 120)
    heights.put("Masha", 100)
    heights.put("Ilya", 110)

    heights("Masha") += 3


    val aaa = 111

  }

  // example of collect function
  def test_7(): Unit = {

    val data = Range(0, 10)
    val d1 = data.filter(_ % 2 == 0).map(_ + 100)
    val d2 = data.collect({ case (el: Int) if (el % 2 == 0) => (el + 100) })

    val aaa = 111;

  }


}


