package SparkBasics

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object BroadCast {

  def test( sc: SparkContext ) = {

      val univs_map = Map(
        ("МГУ","Московский Государственный Университет им. М.В.Ломоносова"),
        ("МГТУ","Московский Государственный Технический Университет им. Н.Э.Баумана"),
        ("МФТИ","Московский Физико Технический Институт"),
        ("MIT", "Massachusetts Institute of Technology"),
        ("Caltech","California Institute of Technology")
      )
      val countries_map = Map(
        ("США","Соединенные Штаты Америки"),
        ("РФ","Российская Федерация"))

    println("BREAK POINT #1: press any key ...")
    val ch1 = scala.io.StdIn.readLine()

      // 1. build broadcast maps
      val bc_univs_map = sc.broadcast(univs_map)
      val bc_countries_map = sc.broadcast(countries_map)

    println("BREAK POINT #2: press any key ...")
    val ch2 = scala.io.StdIn.readLine()

      val data = Seq(("James","Smith","США","MIT"),
        ("Николай","Петров","РФ","МГУ"),
        ("Peter","Williams","США","MIT"),
        ("Мария","Иванова","РФ","МФТИ")
      )

      // 2. get distributed data
      val rdd1 = sc.parallelize(data, 4)

      // DEBUG: print first 10 records from rdd1
      println("rdd1:")
      println(rdd1.take(10).mkString("\n"))

      // 3. transform rdd using broadcast maps
      val rdd2 = rdd1.map( record => {
        val sh_country = record._3
        val sh_univ = record._4
        val full_country = countries_map.get(sh_country).get
        val full_univer = univs_map.get(sh_univ).get
        (record._1,record._2,full_country,full_univer)
      })

    val rdd2_cnt = rdd2.count()

    println("BREAK POINT #3: press any key ...")
    val ch3 = scala.io.StdIn.readLine()

      // print first 10 records from rdd2
      println("rdd2:")
      println(rdd2.take(10).mkString("\n"))

  }

}
