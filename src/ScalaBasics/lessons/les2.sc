
// 1. Именованные аргументы
def print_data(d1: String, d2: String) = {
  println(d1 + " " + d2)
}
print_data("George", "Bush")
print_data("Bush", "George")
print_data( d2 = "Bush", d1 = "George")

// 2. Наборы (tuples)
val person = ("John", 25, 178, 80)
println( person.toString() )
println( person._3 )
val another_person = ("Bill", 40, 162, 50)
var persons = List( person, another_person )
val third_person = ("Peter", 20, 200, 90)
persons = persons ++ List(third_person)

persons.foreach( el => println( el ) )
//persons.foreach( el => println( el._1 ) )
persons.foreach{
  case (name, age, height, weight) =>
    println(name.toUpperCase + "'s age is " + age.toString)
}

// 3. Функции, как аргументы (простейшие примеры map-reduce)
val add_incr = (x: Int) => x + 10
def mult_incr(x: Int) = x * 1.5
val data = Array(11,3,6,7,10,5)
val my_list = List(12,2,14,20,10)
val data_1 = data.map( add_incr )
val data_2 = data.map( mult_incr )
val r_1 = data_1.reduce( (res, next_el) => res + next_el )
val r_2 = data_2.reduce(_+_)
val s1 = data_1.sum
val s2 = data_2.sum
val it1 = data_1.iterator
val it1_sum = it1.reduce(_+_)

def my_aggregator( it: Iterator[Int] ): Int = it.filter( el => el > 5 ).sum
my_aggregator( data.iterator )
my_aggregator( my_list.iterator )


// 4. Функции, возвращающие функции
def nameBuilder(is_man: Boolean, is_married: Boolean = true):
        (String, String) => String = {
  val prefix = if (is_man) "Mr." else if (is_married) "Mrs." else "Miss"
  (first_name: String, second_name: String) =>
    prefix + " " + first_name + " " + second_name
}
def getManName =
  nameBuilder( true )
def getMarriedWomanName =
  nameBuilder( false)
def getNotMarriedWomanName =
  nameBuilder( false, false)

println( getManName("John", "Smith") )
println( getMarriedWomanName("Jane", "Smith") )
println( getNotMarriedWomanName("Alice", "Smith") )


// 5. Множественные списки параметров + foldLeft + generic-методы
def myFoldLeft[A,B](list: Iterable[A], z0: B)(op: (B, A) => B): B = {
  var result = z0
  list.foreach( el => result = op(result, el) )
  result
  //list.foldLeft(z0)( op )
}

val my_list_1 = Array(11,3,6,7,10,5)
val func = (acc: Int, el: Int) => if (el>5) acc+1 else acc
println( myFoldLeft( my_list_1, 0)( func ) )

my_list_1
  .filter( _ > 5 )
  .map( el => 1 )
  .reduce(_+_)


// 6. Частичное применение параметров
def getSmithsMarriedWomanName ( first_name: String ) =
  getMarriedWomanName(first_name, "Smith")
println( getSmithsMarriedWomanName("Masha") )

// 7. Case-классы
case class Student (name: String, second_name: String, var year: Int)

val st1 = Student("Vasya", "Pupkin", 1)
var st2 = Student("Nikolay", "Petrov", 2)
val st3 = st2
val st4 = st2.copy()
st2.year += 1

println( st1 )
println( st2 )
println( st3 )
println( st4 )

val c1 = st1 == st2
val c2 = st3 == st2
val c3 = st4 == st2
val c3 = st3 == st4

// 8. Matching
val x = scala.util.Random.nextInt(10)
val y = x match {
  case 0 => "zero"
  case 1 => "one"
  case 2 => "two"
  case _=> "other"
}


for (i <- Range(1,10)) {  println(i) }

Range(1,10).foreach(println(_))