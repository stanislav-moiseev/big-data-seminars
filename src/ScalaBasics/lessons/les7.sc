/*
// 1. Polymorphic methods
def listOfDuplicates[A](x: A, length: Int): List[A] = {
  if (length < 1)
    Nil
  else
    x :: listOfDuplicates(x, length - 1)
}
println(listOfDuplicates[Int](3, 4))  // List(3, 3, 3, 3)
println(listOfDuplicates[String]("La", 8))  // List(La, La, La, La, La, La, La, La)
*/

/*
// 2. Operators
case class Vec(x: Double, y: Double) {
    def +(that: Vec) = Vec(this.x + that.x, this.y + that.y)
}

val vector1 = Vec(1.0, 1.0)
val vector2 = Vec(2.0, 2.0)

val vector3 = vector1 + vector2
vector3.x  // 3.0
vector3.y  // 3.0
*/

/*
// 3. Operators (cont)
case class MyBool(x: Boolean) {
    def myand(that: MyBool, k: Int): MyBool = if (x) that else this
    def myor(that: MyBool): MyBool = if (x) this else that
    def mynegate: MyBool = MyBool(!x)
}

def not(x: MyBool) = x.mynegate
def myxor(x: MyBool, y: MyBool) = (x myor y).myand( not(x.myand(y,1)), 1 )

val a = MyBool(true)
val b = MyBool(false)
val c = myxor(a, b)
*/

/*
// 4. By-name parameters
def factorial(n: Int) = {
    println("factorial called!")
    List.range(1, n+1).foldLeft(1)(_*_)
}

def calculate(condition: Boolean, input: => Int) = {
    if (condition) input * 1000
    else -1
}

lazy val b = factorial(5)
println("Hello, world!")

val a = calculate(false, b)
//println(s"a = $a")
*/

/*
// 5. annotations
@deprecated
def factorial(n: Int) = {
    println("fatorial called!")
    List.range(1, n+1).foldLeft(1)(_*_)
}

val n = 5
println(s"n! = ${factorial(n)}")

def inc(x: Int, @deprecatedName('old_n) n: Int): Int = x + n
inc(1, old_n=2)
*/

// packages, imports, aliases ...
import java.util.{Date => utilDate}
import java.sql.{Date => sqlDate}

def run(): Unit = {
    val dt: utilDate = new utilDate()
    val dtSql: sqlDate = new sqlDate(System.currentTimeMillis())
    println(s"I am a vehicle running on $dt !")
    println(s"I am a vehicle running on $dtSql !")
}
run()

// hide