// 1. Выражения
val a = 123
var b_var: Int = 12
b_var = a + b_var + 1
println("Hello, World!")

// 2. Блоки
val x = { class MyClass ( val a : Int)
  val obj1 = new MyClass( 12 )
  val tmp1 = 1
  val tmp2 = 2
  tmp1 + tmp2 + obj1.a
}
println(x)

// 3. Функции
val f = (a: Double , b:Double) => a + b
println(f(1,2))
println(f)

// 4. Методы
def func ( x: Int, y:Int ):Double = {
  if (x > 5)
    x+y
  else
    x-y
}
println( func(4, 3) )

// 5. Классы
class MyClass1( val name: String,
               val v1: Int,
               var d1: Double,
               private var d2: String ) {
  def get_d2(): String = d2
  def printName() = {
    println("My name is " + name)
  }
  def printFullName() = {
    println("My full name is " + name + " " + d2)
  }
}

val obj1 = new MyClass1("Vasya", 1, 2.0, "Petrov" )
obj1.printName()
obj1.printFullName()
obj1.d1 = 3.0

// 6. Case-классы
val obj2 = new MyClass1("Vasya", 1, 2.0, "Petrov" )
val obj3 = new MyClass1("Vasya", 1, 2.0, "Petrov" )
println( obj2.equals( obj3 ) )

case class Person(name: String, sec_name:String)
val p1 = Person("Vasya", "Petrov")
val p2 = Person("Vasya", "Petrov")
println( p1 == p2 )
println( p1.toString )

// 7. Объекты
object Physiscs {
  val pi = 3.14
  val e = 2.71
  def getCircleArea( r:Double ):Double = r*r*pi
}

val c1_area = Physiscs.getCircleArea(10.0)
println(c1_area)

// 8. Трейты
trait Animal {
  def tellSmth()
}

class Dog(name: String) extends Animal {
  override def tellSmth() = {
    println("Gav-gav")
  }
}

class Human(name: String) extends Animal {
  override def tellSmth() = {
    println("My name is " + name)
  }
}

val dog1 = new Dog("Bobik")
val hum1 = new Human("Vasya")

dog1.tellSmth()
hum1.tellSmth()

