// 1. Pattern matching

// 1.1. match keyword
val x = "Poka"
x match {
  case "Privet" => print("Hello!\n")
  case "Poka" => print("Bye!\n")
  case _=> "???"
}

// 1.2. Case-class matching
abstract class Animal
case class Cat(name: String, sound: String = "Miau") extends Animal
case class Dog(name: String, sound: String = "Gav") extends Animal
case class Duck( sound: String = "Krya" ) extends Animal

val ans = List( Cat("Murka"), Dog("Murka"), Duck() )

ans.foreach( el => el match {
  case Cat(n1, s1) => println( "Cat " + n1 + " says " + s1 )
  case Dog(n, s) => println( s"Dog $n says $s" )
  case Duck(s) => println( "Duck " + "says " + s )
})

// 1.3. Используем базовые родительские классы
def foo(a: Any) = a match {
  case s:String =>  println( s"found string - $s" )
  case n:Number => println( s"found number - $n" )
  case (a,b) => println( s"found tuple - ($a,$b)" )
  case _ =>  println( s"found something other" )
}

foo(2)
foo(2.2)
foo((5.1).asInstanceOf[Float])
foo("Valera")
foo( (1,"Vasya"))
foo( new Array[Int](1) )

// 1.4. Patter-matching + if
abstract class Notification
case class SMS(ph_num: Int, str: String) extends Notification
case class Email(from: String, str: String) extends Notification
case class MissedCall( ph_num: Int ) extends Notification
case class WhatsAppMsg( from: String, str: String ) extends Notification

def getSimpleNotification(notification: Notification): String = {
  "received - " + notification.toString
}

def getNotification(notification: Notification,
                    importantPeopleInfo: Seq[String]): String = {
  notification match {
    case Email(from, _) if importantPeopleInfo.contains(from) =>
      "Important Email received! - " + notification.toString
    case SMS(ph_num: Int, _) if importantPeopleInfo.contains(ph_num.toString) =>
      "Important SMS received! - " + notification.toString
    case other => getSimpleNotification(other)
  }
}

val msg1 = SMS(456, "How are you?")
val msg2 = WhatsAppMsg("456", "How are you?")

val importantPeople = Seq("456", "vasya@gmail.com")
val n_list = Seq( SMS(123, "How are you?"),
  SMS(456, "How are you?"),
  Email("vasya@gmail.com", "Privet!"),
  Email("petya@gmail.com", "Privet ot Peti!"),
  WhatsAppMsg("456", "Privet from WhatApp")
)
n_list.foreach( el => println( getNotification( el, importantPeople ) ) )

// 2. Объекты

// 2.1 Объекты как хранилища статических методов и
object Arithmetic {
  def factor(k: Int): Int = {
    var res = 1
    Range(1,k+1).foreach(el => res = res * el )
    res
  }
  val PI = 3.14159
}
println( Arithmetic.factor(0) )
println( Arithmetic.factor(3) )
println( Arithmetic.factor(5) )
println( Arithmetic.PI )

println( math.Pi )


// 2.2. Компаньоны (класс-объект)
object Circle {
  private def calculateArea(radius: Double): Double = math.Pi * math.pow(radius, 2.0)
}

case class Circle(private val radius: Double) {
  import Circle.calculateArea
  def area(): Double = calculateArea(radius)

}

val circle1 = Circle(5.0)
println(circle1.area)