import ScalaTest.Matrix3
import org.scalatest.funsuite.AnyFunSuite

class Matrix3Suite extends AnyFunSuite  {

  test("Addition of matrices") {
    val m1 = new Matrix3(1,2,3)
    val m2 = new Matrix3(0,0,1)
    val m3 = m1.add( m2 )
    val m4 = new Matrix3(1,2,4)
    assert( m3.equals(m4) )
  }

}
