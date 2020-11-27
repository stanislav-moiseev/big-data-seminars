// Scala Standard Library

// 1. Mutable & Immutable Collections

/*
// 0. arrays
val myArr = (1 to 10).toArray
myArr.foreach( el => println(el) )
myArr(0)
myArr(5) = 25
myArr.foreach( el => println(el) )

val myArrB = new Array[Byte](10)
myArrB.foreach( el => println(el) )
*/

/*
// 1. some examples
// immutable List
val myList = List(1,2,3,4,5,6)
myList.foreach(el => println(s"$el"))

// mutable List
val myListm = scala.collection.mutable.ListBuffer(1,2,3,4)
myListm.foreach(el => println(s"$el"))
myListm.insert(2, 10)
myListm.foreach(el => println(s"$el"))

// immutable Set
val mySet = Set("A", "B", "C")
val next = mySet.map(el => el.toLowerCase)
next.foreach(el => println(s"$el"))

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
val s1 = Set("x", "y", "z")
val s2 = mutable.Set("x", "y", "z")

val mySeq = (1 to 10).toSeq
mySeq(5)
*/

/*
// 2. trait Iterable
val list1 = List(1, 2, 3, 4, 5)
val it1 = list1.iterator
val git = it1.grouped(3)
git.next()
git.next()
//git.next()

val it1_1 = list1.iterator
val sit = it1_1.sliding(3)
sit.next()
sit.next()
sit.next()
//sit.next()

val it1_3 = list1.iterator
val list2 = List(10,20,30)
val it2 = list2.iterator
val it3 = it1_3 ++ it2
while (it3.hasNext)
  println( it3.next() )
*/

/*
// 1. size
val myList = 1 to 10
myList.size
myList.isEmpty
myList.count( _ > 5 )
*/

/*
// 2. map/flatMap/collect
val my_list = List(1, 2, 3, 4, 5)
val f1 = ( a: Int ) => math.sqrt(a)
val f2 = ( a: Int ) => Range(0, a).toList
val f3 = new PartialFunction[Int, Int] {
  def apply(x: Int) = x
  def isDefinedAt(x:Int) = x%2==0
}
val f4: PartialFunction[Int, Int] = {
  case x if x%2==1 => x
}

val r1 = my_list.map(f1)
val r2 = my_list.map(f2)
val r3 = my_list.flatMap(f2)
val r5 = my_list.collect(f3)
val r6 = my_list.collect(f4)
*/

/*
// 3. folds
val my_list = 1 to 10
val r1 = my_list.reduceLeft( (acc, nextEl) => {if (nextEl % 2 == 0) acc+nextEl else acc} )
val r2 = my_list.foldLeft("")( (acc, nextEl) => {if (nextEl % 2 == 0) acc+" "+nextEl.toString else acc} )
val r3 = my_list.reduce( (a,b) => a+b )
val r4 = my_list.reduce( _+_ )
*/

/*
// 4. zippers
val my_list = (1 to 10).map( el =>  "*" + el.toString + "*")
val l2 = my_list.zipWithIndex
val l3 = l2.map( el => el.swap )
*/

/*
// 5. sections/views
val my_arr = (1 to 10).toArray
val sec1 = my_arr.slice(4, 6)
val v1 = my_arr.view(4, 6)
my_arr(4) = 100
my_arr(5) = 1000
my_arr.foreach( el => println(el) )
sec1.foreach( el => println(el) )
v1.foreach( el => println(el) )
*/

/*
// 6. copying to arrays/buffers
val myList = 1 to 10
val myArr = new Array[Int](5)
myList.copyToArray(myArr)
myArr.foreach( el=>println(el) )

import scala.collection.mutable.ArrayBuffer
val myBuff = new ArrayBuffer[Int](0)
myList.copyToBuffer(myBuff)
myBuff.foreach( el=>println(el) )
*/