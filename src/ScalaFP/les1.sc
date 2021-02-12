/*
// 1. isSorted
def isSorted[A]( ar: Array[A], orPred: (A,A)=>Boolean  ):Boolean = {
  var res = true
  var finished = false
  var i = 1
  while ((i < ar.size)&&(!finished)) {
    if ( ! orPred(ar(i-1), ar(i)) ) {
      finished = true;
      res = false;
    }
    i = i+1
  }
  res
}

val myOr: (Int, Int)=>Boolean = _ < _
val res1 = isSorted( Array(5,4,6,8), myOr )
val res2 = isSorted( Array.empty[Int], myOr )
*/

/*
// 2. partial
def partial1[A,B,C](a:A, f: (A,B)=>C ): (B=>C) =
  (b:B) => f(a,b)

val myAdd = (a:Int, b:Int) => a+b
val myAddPart = partial1(3, myAdd)
val res = myAddPart(10)
*/

/*
// 3. currying/uncurrying
val myAdd = (a:Int, b:Int) => a+b
def curry[A,B,C](f: (A,B)=>C ): A => (B=>C) =
  (a:A) => ( (b:B) => f(a,b) )

val mac = curry(myAdd)
val res1 = mac(3)(7)

def uncurry[A,B,C](f: A=>(B=>C) ): (A,B) => C =
  (a:A, b:B) => ( f(a)(b) )

val mauc = uncurry(mac)
val res2 = mauc(13,27)
*/

/*
// 4. compose
val duplicate = (a:Double) => a*2.0
val circArea = (r:Double) => math.Pi*r*r

def compose[A,B,C]( f:B=>C, g: A=>B ): A=>C =
  (a:A) => f( g(a) )

val fc1 = compose( circArea, duplicate )
val fc2 = compose( duplicate, circArea )
val res1 = fc1(1)
val res2 = fc2(1)

val fc3 = circArea.compose(duplicate)
val res3 = fc3(1)

val fc4 = duplicate.andThen(circArea)
val res4 = fc4(1)
*/

// 5. pattern matching
def sum(data: List[Int]): Int = data match {
  case Nil => 0
  case 0 :: xs => xs.sum + 1000
  case x :: xs => x + sum(xs)
}

val myList = Range(0,5).toList
val s = sum(myList)

myList.foldLeft()




