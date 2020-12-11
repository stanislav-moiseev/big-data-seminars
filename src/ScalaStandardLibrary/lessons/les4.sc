import scala.reflect.ClassTag
// TypeTags & ClassTags

/*
// 1. inspect type members
import scala.reflect.runtime.{universe => ru}
val myList = List(1,2,3)
def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]
val theType = getTypeTag(myList).tpe
theType.decls
*/

// 2. create array of type which is specified by type parameter
import scala.reflect.runtime.universe._

def paramInfo[T](x: T)(implicit tag: TypeTag[T]): Unit = {
  val targs = tag.tpe match { case TypeRef(_, _, args) => args }
  println(s"type of $x has type arguments $targs")
}