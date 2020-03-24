package SparkSerialization

import SparkBasics.SharedSparkContext

object Main extends SharedSparkContext{

  def main(args: Array[String]): Unit = {

    SerializationTests.test_2( sc )

  }

}
