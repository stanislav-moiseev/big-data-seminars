/*
// 7. sub-collections
val myColl = (1 to 10).to[List]
println(myColl.head)
println(myColl.take(3))
println(myColl.tail)
println(myColl.drop(2))
myColl
println(myColl.filter( el => el%2 != 0 ))

val myColl2 = myColl.groupBy( el => (el%3).toString )
myColl2("1")
myColl2.foreach( { case (k,v) => println(s"key=$k - val=$v") } )

val subseq1 = (1 to 100).takeWhile( el => el%7 < 5 )
val subseq2 = (1 to 100).dropWhile( el => el%7 < 5 )
val (c1, c2) = (1 to 10).splitAt(4)


def pred_sqr (el: Int): Boolean = {
  val t = math.pow(el.toDouble, 1.0 / 2.0)
  (math.abs(math.round(t) - t) <= 1.0e-12)
}

val pred_cube = new Function1[Int, Boolean] {
  def apply(el: Int): Boolean = {
    val t = math.pow(el.toDouble, 1.0 / 3.0)
    (math.abs(math.round(t) - t) <= 1.0e-12)
  }
}

val (p1, p2) = (1 to 100)
  .partition( pred_sqr )
val f1 = (1 to 100)
  .filter( pred_sqr )

val (q1, q2) = (1 to 100)
  .partition( pred_cube )
*/

/*
// 1. Seq trait, length, indexing

// sequences have length, and apply()
// but complexity of indexedSeq and linearSeq
// of apply() is different

val iSeq = (1 to 10)
val len = iSeq.length
val elem = iSeq(5)

val lSeq = (1 to 10).toList
lSeq(3)
lSeq.length
*/

/*
// 2. Search
val lSeq = (1 to 10).toList
lSeq.indexOf(6)

val data = Range(0, 10, 2)
data.indexOf(6)

val c1 = Range(0, 10, 2)
val c2 = Range(0, 10, 2)
val c3: Range = c1 ++ c2
c3.lastIndexOf(6)

def pred_sqr (el: Int): Boolean = {
  val t = math.pow(el.toDouble, 1.0 / 2.0)
  (math.abs(math.round(t) - t) <= 1.0e-12)
}
c3.lastIndexWhere( pred_sqr )
*/

/*
// 3. Concatenations & Updates
val c1 = Range(1, 6)
val c2 = c1 :+ 4
val c3 = 4 +: c1
val c4 = c1 ++ c1
val c5 = c1.updated(2,99)

import scala.collection.mutable
val c6 = mutable.ArrayBuffer.fill(10)(0)
c6.length
c6(5)=99
c6.insert(6, 11, 12, 13)
c6
 */


// 4. sorting & ordering
val c1 = Range(1, 10)
val cmpFunc = (a: Int, b:Int) => a>b
val iCmpFunc = (a: Int, b:Int) => !cmpFunc(a,b)

val c3 = c1.sortWith( cmpFunc )
c1

import scala.collection.mutable
var c2 = mutable.ArrayBuffer(1,5,100,4,2)
c2(4)=77
c2
//c2.sortInPlace()
//c2

//val c3 = mutable.ArrayBuffer.fill(3)(0)
//c2 = c3

c2.appendAll( List(1,2,3) )
c2.prependAll( List(-1,-2,-3) )
c2
c2.insertAll( 7, List(0,0,0) )
c2



