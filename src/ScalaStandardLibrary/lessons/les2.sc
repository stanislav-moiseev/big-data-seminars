
// 7. sub-collections
val myColl = (1 to 10).toList
println(myColl.head)
println(myColl.take(3))
println(myColl.tail)
println(myColl.drop(2))
println(myColl.filter( el => el%2 != 0 ))

val myColl2 = myColl.groupBy( el => el%3 )
myColl2.foreach( { case (k,v) => println(s"key=$k - val=$v") } )

val subseq1 = (1 to 10).takeWhile( el => el%7 < 5 )
val subseq2 = (1 to 10).dropWhile( el => el%7 < 5 )
val (c1, c2) = (1 to 10).toList.splitAt(4)


def pred_sqr (el: Int): Boolean = {
  el.toDouble == math.pow(math.sqrt(el),2.0)
}

val pred_cube = new Function1[Int, Boolean] {
  def apply(el: Int): Boolean = {
    val t = math.pow(el, 1.0 / 3.0)
    ((math.floor(t) - t) == 0)
  }
}

val (p1, p2) = (1 to 10)
  .partition( pred_sqr )

val (q1, q2) = (1 to 10)
  .partition( pred_cube )