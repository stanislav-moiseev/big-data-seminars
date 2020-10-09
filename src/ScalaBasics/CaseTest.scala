package ScalaBasics

object CaseTest {

  def main(args: Array[String]): Unit = {
    test_1
    //test_2
    test_3
  }

  // declare class and create messages
  def test_1(): Unit = {
    class Message( val from:String, val to:String, val contents:String )
    val m1 = new Message( "Anna", "Mary", "How R U?" )
    val m2 = new Message( "Anna", "Mary", "How R U?" )
    val m3 = new Message( "Mary", "Anna", "Fine! Thanks" )
    val r1 = (m1==m2)  // false
    val r2 = (m2==m3)  // false

    val m4 = m1
    val r4 = (m4 == m1)

    val i_1 = 1
    val d_1 = 1.0
    val cmp = (i_1 == d_1)

    val r5 = 111
  }

  // declare case class and create messages
  def test_2(): Unit = {
    case class Message( val from:String, val to:String, val contents:String )
    // note: no need to use new for case class
    val m1 = Message( "Anna", "Mary", "How R U?" )
    val m2 = Message( "Anna", "Mary", "How R U?" )
    val m3 = Message( "Mary", "Anna", "Fine! Thanks" )
    val r1 = (m1==m2)  // true  (case classes are compared by structure)
    val r2 = (m2==m3)  // false
    val r4 = 111
  }

  // copying objects of case classes
  def test_3(): Unit = {

    // vars is case classes are allowed but discouraged
    case class Message( val msg:String, var data:String )

    val m1 = Message( "M1", "12345" )
    val m2 = m1  // m2 is actually a reference to m1
    val m3 = m1.copy()  // m3 is a shallow copy of m1
    m1.data = "-----"

    val r4 = 111
  }

}
