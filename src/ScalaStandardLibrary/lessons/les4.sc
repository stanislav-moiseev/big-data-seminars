import javassist.bytecode.stackmap.TypeTag
import scala.reflect.ClassTag

// TypeTags & ClassTags

/*
// 0. reminder of how flatten works
val list = List( List("str1", "str2"), List("str3", "str4"))
val res = list.flatten

val list2 = List(Some("string1"), None, Some("string3"))
val res2 = list2.flatten
*/


/*
// 1. extract only strings from list of Anys
def extract[T](list: List[Any]) = list.flatMap {
  case element: T => Some(element)
  case _ => None
}

val list = List(1, "string1", List(), "string2")
val result = extract[String](list)
println(result)   // doesn't work
*/

/*
// 2. second attempt
def extract(list: List[Any]) = list.flatMap {
  case element: String => Some(element)
  case _ => None
}
val list = List(1, "string1", List(), "string2")
val result = extract(list)
println(result)   // this works
*/

/*
// 2. set type upper bound
def extract[T <: String](list: List[Any])
= list.flatMap {
  case element: T => Some(element)
  case _ => None
}
val list = List(1, "string1", List(), "string2")
val result = extract[String](list)
//val result = extract[Int](list)
println(result)   // does work
*/

/*
// 3. add class tag
def extract[T](list: List[Any])(implicit tag: ClassTag[T])
= list.flatMap {
  case element: T => Some(element)
  case _ => None
}
val list = List(1, "string1", List(), "string2")
//val result = extract[String](list)
val result = extract[Any](list)
println(result)   // does work
*/

/*
// 4. problem is still here :)
def extract[T](list: List[Any])(implicit tag: ClassTag[T])
= list.flatMap {
  case element: T => Some(element)
  case _ => None
}
val list: List[List[Any]] = List(List(1, 2), List("a", "b"))
val result = extract[List[Int]](list)
println(result)
// ClassTag only has information about top level type
*/

/*
// 5. inspect type members
import scala.reflect.runtime.{universe => ru}
val myList = List(1,2,3)

def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]
val theType = getTypeTag(myList).tpe
theType.decls.foreach( println(_) )
*/

// 6. create instance of specified type
import scala.reflect.runtime.{universe => ru}

object MyApp {
  case class Person(val name: String)
  val p1 = Person("Vasya")

  def main {
    val mirr = ru.runtimeMirror( this.getClass.getClassLoader )

    val classSym = ru.typeOf[Person].typeSymbol.asClass
    val classMirr = mirr.reflectClass(classSym)

    val constrSym = ru.typeOf[Person].decl(ru.termNames.CONSTRUCTOR).asMethod
    val constructorMirr = classMirr.reflectConstructor(constrSym)

    val p2 = constructorMirr("Anna")
    println(p2)

    // direct access to fields of immutables

    // 1. this doesn't work
    // p1.name = "Petya"

    // 2. second attempt
    val instMirror = mirr.reflect(p1)
    val fieldSymb = ru.typeOf[Person].decl(ru.TermName("name")).asTerm
    val fieldMirror = instMirror.reflectField(fieldSymb)

    println( fieldMirror.get )
    fieldMirror.set("Kolya")
    println(p1)

  }
}

MyApp.main