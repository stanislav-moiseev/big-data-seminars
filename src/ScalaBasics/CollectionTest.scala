package ScalaBasics

import scala.util.Random

object CollectionTest {

  def main(args: Array[String]): Unit = {

    //test_1_views()
    //test_2_type_spec()
    //test_3
    test_4()

  }

  def test_1_views(): Unit = {
    // create list with given min, max, step
    val my_list_1 = List.range(0,10, 2)
    val tr_res_1 = my_list_1.map( a => 2*a )

    // create list and view
    val my_list_2 = List.range(0, 10)
    val v1 = my_list_2.view
    val v2 = v1.map( a => 2*a )
    val v3 = v2.map( a => a+1 ).force
    v3.foreach( el => println(s"$el") )

  }

  // example how to create parametrized collections
  def test_2_type_spec(): Unit = {

    val data = new Array[String](10)
    for ( i <- 0 to data.length-1 )
      data(i) = Random.nextInt().toString

    val data2 = new Array[Int](10)
    for ( i <- 0 to data2.length-1  )
      data2(i) = Random.nextInt()

    val aaa = 111
  }

  // concatenation of lists
  def test_3(): Unit = {

    val l1 = List(1,2,3)
    val l2 = List(4,5)

    val l3 = l1 ++ l2

    val l3_c = l3.foldLeft(0)( (acc, el) => acc + el )

    val aaa = 111
  }

  // tests with sets
  def test_4(): Unit = {

    val set_m = scala.collection.mutable.HashMap( (1, "Batman"), (2, "Superman"), (3, "Spiderman") )
    set_m.+=( (5, "Daredevil") )

    val a = 111


  }

}
