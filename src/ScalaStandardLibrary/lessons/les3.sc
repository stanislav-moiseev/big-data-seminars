import scala.collection.immutable.HashMap
import scala.collection.{IterableView, mutable}
import scala.util.Random
// Maps

/*
// 1. basic functionality
//val myMap = Map("Anna" -> 20, "Vasya" -> 15, "Maria" -> 30)
val myMap = Map( ("Anna",20) , ("Vasya", 15) ,
  ("Maria",20) )

println( myMap.apply("Maria") )

val res = myMap.get("Maria")
if (res.isDefined) res.get
res.getOrElse(-1)

val a = myMap.getOrElse("Anna", -1)
val b = myMap.getOrElse("Maria", -1)
val c = a + b

val myMap2 = Map("abc" -> 123, "Maria" -> 12)

val myMap3 = myMap2 ++ myMap
myMap3.foreach( println(_) )

val myMap4 = (myMap3 - "abc")
myMap4.foreach( println(_) )
*/

/*
// 2. small sized maps
val map: Map[Int, Int] = Map(1 -> 2, 2 -> 3, 3 -> 4)
val map2: Map[Int, Int] = Map(1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5, 5 -> 6)

println(map.getClass.getName)
println(map2.getClass.getName)
*/

/*
// 3. create large maps from scratch
case class Student (name: String, age: Int)
val students = List( Student("Anna", 20),
  Student("Kolya", 21), Student("Vasya", 19))

val stMap = students.map(el => (el.name, el.age)).toMap
stMap.foreach( println(_) )
*/

/*
// 4. Bonus :) repeated parameters & sequence expansion
def awesomePrint(els: String*) = {
  els.foldLeft("")( (acc,el) => acc + "|" + el )
}
awesomePrint( "123", "456" )

val myColl = List("Hello", "to", "the", "whole", "word", "!")
awesomePrint( myColl: _* )
*/

/*
// 5. mutable maps
val myMap = Map( ("Anna",20) , ("Vasya", 15) ,
  ("Maria",20) )

import scala.collection.mutable
val mutMap = mutable.HashMap( myMap.toList: _* )
mutMap.+=( ("Petya", 33)  )
val keysSet = mutMap.keySet

keysSet.foreach(println(_))
mutMap.put( "aaa" , 555 )
keysSet.foreach(println(_))

val keysSetCopy = Array.fill(keysSet.size)("")
keysSet.copyToArray(keysSetCopy)
keysSetCopy.foreach(println(_))

mutMap.put( "bbb" , 111 )
keysSet.foreach(println(_))
keysSetCopy.foreach(println(_))
*/

/*
// 7. map views/slices
val myKeys = Range(1,20)
val myVals = myKeys.map( el => math.abs( Random.nextInt() ) % 10 )
val dict = mutable.HashMap( myKeys.zip(myVals): _* )
dict.foreach(println(_))

val resv = dict.view(4,8)
resv.foreach( el => println(el) )

val ress = dict.slice(4,8)
ress.foreach( el => println(el) )

dict.put( 555, -1 )
resv.foreach( el => println(el) )
ress.foreach( el => println(el) )
*/

/*
// 7. map updates
val myKeys = Range(1,10)
val myVals = myKeys.map( el => math.abs( Random.nextInt() ) % 5 )
val dict = mutable.HashMap( myKeys.zip(myVals): _*)
dict.foreach(println(_))

//dict.update(5, -1)
dict(5) = -1
dict.foreach(println(_))

dict.remove(4)
dict.foreach(println(_))

dict(1) = 333
dict.foreach(println(_))

dict.clear()
*/

// 8. map based cache
def profile[R](code: => R, t: Long = System.nanoTime) =
  (code, System.nanoTime - t)

def reverse(x: String) = {
  Thread.sleep(1000)
  x.reverse
}

val data = Range(1,21).map( el =>
  10 + math.abs(Random.nextInt()) % 5 )
data.foreach(println(_))

// no cache
val (r1, t1) = profile({
  data.foreach(el => {
    val word = el.toString
    val res = reverse(word)
    print(res + " ")
  })
  println();
})
println(s"Duration = ${t1*1.0e-9} seconds")

// with cache
val (r2, t2) = profile({
  val cache = mutable.HashMap.empty[String, String]
  data.foreach(el => {
    val word = el.toString
    // note that value is by-name parameter
    val res = cache.getOrElseUpdate(word, reverse(word))
    print(res + " ")
  })
  println();
})
println(s"Duration = ${t2*1.0e-9} seconds")


