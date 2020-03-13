package Demo

import org.apache.spark.sql.SparkSession

import math.random
import math.min

object L2_CharWordCounter {

  //---------------------------------------------------------------------------
  def count_letters(spark: SparkSession, filename: String, num: Int ) {

    val lines_rdd = spark.sparkContext.textFile(filename)

    /* Define the set of all words starting with the giving prefix. */
    val chars_rdd =
      lines_rdd
        .flatMap(line => line.toCharArray())
        .map( char => char.toLower )
        .filter( char => "абвгдежзийклмнопрстуфхцчшщъыьэюя ".contains(char) )

    val num_chars = chars_rdd.count

    /* Get the list of pairs (word, number_of_occurrences). */
    val stats: Array[(Char, Int)] =
      chars_rdd
        .map( char => (char, 1))
        .reduceByKey(_ + _)
        .collect

    printf("Here are %,d/%,d most popular characters:\n\n",
      min(num, stats.length), stats.length )

    stats
      .sortWith(_._2 > _._2)
      .take(num)
      .map({ case (char, counter) => printf("%20s   => %6.2f%% (%,d / %,d)\n",
        char, 100.0 * counter / num_chars,
        counter, num_chars)
      })
  }

  //---------------------------------------------------------------------------
  def count_words(spark: SparkSession,
                  filename: String,
                  prefix: String,
                  min_len: Int,
                  max_len: Int,
                  num: Int) {

    val lines_rdd = spark.sparkContext.textFile(filename)

    val words_rdd =
    lines_rdd
      .flatMap(line => line.split("[  ,;.…:–—?!«»()\\[\\]]"))   /* Map every line into a sequence of words. 
                                                                 * The regexp defines the set of separating characters.
                                                                 * This might generate empty words. */
      .map(word => word.toLowerCase)
      .filter(word => !word.isEmpty && word.take(prefix.length) == prefix)


  /* Ask Spark to count the total number of words.  This will trigger a
   * computation. */
  val num_words = words_rdd.count

  /* Get the list of pairs (word, number_of_occurrences). */
  val stats: Array[(String, Int)] =
    words_rdd
      .map(word => (word, 1))

      .reduceByKey(_ + _)       /* 'reduceByKey' forms an RDD by collecting all pairs with identical keys
                                 * and applying the reducing function to their keys,
                                 * e.g. reduceByKey(_+_): [("a",2), ("b",1), ("b",1), ("a",3)] => [("a",5), ("b",2)] */

      .collect                  /* 'collect' will force a computation and return the result as a Scala object. 
                                 * The order of objects in the resulting array is unspecified. */


  /* Print top 'num' most popular words. */
  printf("Here are %,d/%,d most popular words starting with '%s':\n\n",
         min(num, stats.length), stats.length, prefix)
  
  stats
    .sortWith(_._2 > _._2)
    .take(num)
    .map({case (word, counter) => printf("%20s   => %6.2f%% (%,d / %,d)\n",
                                         word, 100.0 * counter / num_words,
                                         counter, num_words)})
}

}