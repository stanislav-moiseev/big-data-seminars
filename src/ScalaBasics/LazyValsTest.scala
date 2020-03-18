package ScalaBasics

object comicBook {
  def main(args:Array[String]): Unit = {

    val s1 = gotham.hero
    val s2 = gotham.villain
    
    gotham.hero.talk()
    gotham.villain.talk()

    gotham.hero.talk()
    gotham.villain.talk()

    gotham.hero.talk()
    gotham.villain.talk()

    val aaa  = 111

    gotham.my_function( {println("x func called"); 1} )

    gotham.my_function( {println("x func called"); 1} )

    gotham.my_function( {println("x func called"); 1} )

    val bbb = 111


  }
}

class Superhero(val name: String, val strength: Int) {
  lazy val opponent1 = { println("lazy val initialized! "); gotham.villain }
  def talk(): Unit = {
    println(s"I won't let you win ${opponent1.name}!")
  }

  println("Super created!!!")
}

class Supervillain(val name: String) {
  lazy val opponent = gotham.hero
  def talk(): Unit = {
    println(s"Let me loosen up Gotham a little bit ${opponent.name}!")
  }

  println("Super created!!!")
}

object gotham {
  val hero: Superhero = new Superhero("Batman", 10)
  val villain: Supervillain = new Supervillain("Joker")

  def my_function ( x: => Int ): Int = {
    //return x + x
    val y = x
    return y + y
  }
}

