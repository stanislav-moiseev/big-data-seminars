package ScalaBasics

object CollectionTest {

  def main(args: Array[String]): Unit = {

    test_1_views()

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


}
