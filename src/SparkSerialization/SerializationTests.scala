package SparkSerialization

import org.apache.spark.SparkContext

//-----------------------------------------------------------------------------
class my_class (val id: Int,
                val data_i: Int,
                val data_d:Double)
{}

//-----------------------------------------------------------------------------
class my_class_ser (val id: Int,
                    val data_i: Int,
                    val data_d:Double)
extends Serializable
{}

//-----------------------------------------------------------------------------
object SerializationTests {

  // test 1 - create simple rdd, perform map and collect operations
  def test_1 ( sc: SparkContext ): Unit = {

    val testRDD = sc.parallelize( Range(0,10) )

    val testRDD_updt = testRDD.map( _ + 1 )

    val res = testRDD_updt.collect()

    val aaa = 111
  }

  // test 2 - create rdd of my objects
  def test_2 ( sc: SparkContext ): Unit = {

    val rdd = sc.parallelize( Range(0,10) )

    // case 1
    // val rdd_updt_1 = rdd.map( id => new my_class( id, 0, 0 ) )
    // rdd_updt_1.count
    // val res_1 = rdd_updt_1.collect()   // here exception occurs - object my_class is not serializable

    // case 2
    val rdd_updt_2 = rdd.map( id => new my_class_ser( id, 0, 0 ) )
    rdd_updt_2.count
    val res_2 = rdd_updt_2.collect()

    val aaa = 111
  }
}

////-----------------------------------------------------------------------------
//class SparkSerializationSpec
//  extends SharedSparkContext {
//
//  lazy val testRdd = sc.parallelize(List(1, 2, 3))
//
//  //"Some spark serialization examples" should {
//  //  "Example 1 - basic spark map" in {
//  object Example {
//    def myFunc = testRdd.map(_ + 1).collect.toList //shouldBe List(2, 3, 4)
//  }
//
//  Example.myFunc
//
//    //}
//
////    //"Example 2 - spark map with external variable e.g.1" in {
////      object Example {
////        val num = 1
////        def myFunc = testRdd.map(_ + num).collect.toList //shouldBe List(2, 3, 4)
////      }
////      Example.myFunc
////    //}
////
////    "Example 3 - spark map with external variable e.g.2." in {
////      object Example extends Serializable {
////        val num = 1
////        def myFunc = testRdd.map(_ + num).collect.toList //shouldBe List(2, 3, 4)
////      }
////      Example.myFunc
////    }
////
////    "Example 4 - spark map with external variable e.g.3." in {
////      object Example {
////        val num = 1
////        def myFunc = {
////          lazy val enclosedNum = num
////          testRdd.map(_ + enclosedNum).collect.toList //shouldBe List(2, 3, 4)
////        }
////      }
////      Example.myFunc
////    }
////
////    "Example 5 - spark map with external variable e.g.4." in {
////      object Example {
////        val num = 1
////        def myFunc = {
////          val enclosedNum = num
////          testRdd.map(_ + enclosedNum).collect.toList //shouldBe List(2, 3, 4)
////        }
////      }
////      Example.myFunc
////    }
////
////    "Example 6 - nested object e.g.1" in {
////      object Example {
////        val outerNum = 1
////        object NestedExample extends Serializable {
////          val innerNum = 10
////          def myFunc =
////            testRdd.map(_ + innerNum).collect.toList shouldBe List(11, 12, 13)
////        }
////      }
////      Example.NestedExample.myFunc
////    }
////
////    "Example 7 - nested object e.g.2" in {
////      object Example {
////        val outerNum = 1
////        object NestedExample extends Serializable {
////          val innerNum = 10
////          def myFunc =
////            testRdd.map(_ + outerNum).collect.toList //shouldBe List(2, 3, 4)
////        }
////      }
////      Example.NestedExample.myFunc
////    }
////
////    "Example 8 - nested object e.g.3" in {
////      object Example {
////        val outerNum = 1
////        object NestedExample extends Serializable {
////          val innerNum = 10
////          val encOuterNum = outerNum
////          def myFunc =
////            testRdd.map(_ + encOuterNum).collect.toList //shouldBe List(2, 3, 4)
////        }
////      }
////      Example.NestedExample.myFunc
////      // Bonus question - what is being serialized in this example?
////    }
////
////    "Example 9 - adding some complexity e.g.1 - base example" in {
////      object Example {
////
////        class WithFunction(val num: Int) {
////          def plusOne(num2: Int) = num2 + num
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc =
////            testRdd.map(reduceInts).collect.toList //shouldBe List(2, 3, 4)
////        }
////
////        def run = {
////          val withFunction = new WithFunction(1)
////          val withSparkMap = new WithSparkMap(withFunction.plusOne)
////          withSparkMap.myFunc
////        }
////      }
////      Example.run
////    }
////
////    "Example 10 - adding some complexity e.g.2 - make classes serializable" in {
////      object Example {
////
////        class WithFunction(val num: Int) extends Serializable {
////          def plusOne(num2: Int) = num2 + num
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) extends Serializable {
////          def myFunc =
////            testRdd.map(reduceInts).collect.toList //houldBe List(2, 3, 4)
////        }
////
////        def run = {
////          val withFunction = new WithFunction(1)
////          val withSparkMap = new WithSparkMap(withFunction.plusOne)
////          withSparkMap.myFunc
////        }
////      }
////      Example.run
////    }
////
////    //"Example 11a - adding some complexity e.g.3a - use anon function" in {
////      object Example {
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            testRdd
////              .map { e =>
////                reduceInts(e)
////              }
////              .collect
////              .toList //shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap(num => num + 1)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    //"Example 11b - adding some complexity e.g.3b - use anon function, with enclosing" in {
////      object Example {
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap(num => num + 1)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 12a - adding some complexity e.g.4a - use function with def" in {
////      object Example {
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          def addOne(num: Int) = num + 1
////          val withSparkMap = new WithSparkMap(num => addOne(num))
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 12b - adding some complexity e.g.4b - use function with val" in {
////      object Example {
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val addOne = (num: Int) => num + 1
////          val withSparkMap = new WithSparkMap(num => addOne(num))
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 12c - adding some complexity e.g.4c - use function with val explained part 1" in {
////      object Example {
////        val one = 1
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val addOne = (num: Int) => num + one
////          val withSparkMap = new WithSparkMap(num => addOne(num))
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 12d - adding some complexity e.g.4d - use function with val explained part 2" in {
////      object Example {
////        val one = 1
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val oneEnc = one
////          val addOne = (num: Int) => num + oneEnc
////          val withSparkMap = new WithSparkMap(num => addOne(num))
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 13 - adding some complexity e.g.5 - back to the problem, no class params" in {
////      object Example {
////        class WithFunction {
////          val plusOne = (num2: Int) => num2 + 1
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap((new WithFunction).plusOne)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 14 - adding some complexity e.g.6 - back to the problem, with class params" in {
////      object Example {
////        class WithFunction(val num: Int) {
////          val plusOne = (num2: Int) => num2 + num
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap(new WithFunction(1).plusOne)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 15a - adding some complexity e.g.7a - back to the problem, with class params, and enclosing" in {
////      object Example {
////        class WithFunction(val num: Int) extends Serializable {
////          val plusOne = {
////            val encNum = num
////            num2: Int => num2 + encNum
////          }
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap(new WithFunction(1).plusOne)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
////
////    "Example 15b - adding some complexity e.g.7b - back to the problem, with class params, and other enclosing" in {
////      object Example {
////        class WithFunction(val num: Int) {
////          val plusOne = { num2: Int =>
////          {
////            val encNum = num
////            num2 + encNum
////          }
////          }
////        }
////
////        class WithSparkMap(reduceInts: Int => Int) {
////          def myFunc = {
////            val reduceIntsEnc = reduceInts
////            testRdd
////              .map { e =>
////                reduceIntsEnc(e)
////              }
////              .collect
////              .toList shouldBe List(2, 3, 4)
////          }
////        }
////
////        def run = {
////          val withSparkMap = new WithSparkMap(new WithFunction(1).plusOne)
////          withSparkMap.myFunc
////        }
////      }
////
////      Example.run
////    }
// }

