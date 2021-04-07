package ScalaTest

class Matrix3 ( val x: Double,
                val y: Double,
                val z: Double) {

  def add( other: Matrix3 ): Matrix3 = {
    new Matrix3 (x + other.x, y + other.y, z + other.z)
  }

  def equals(other: Matrix3): Boolean = {
    return (this.x == other.x)&&
      (this.y==other.y)&&
      (this.z==other.z)
  }

  def print() = println(s"matrix: $x, $y, $z")

  val aaa = Array(1,2,3)



}
