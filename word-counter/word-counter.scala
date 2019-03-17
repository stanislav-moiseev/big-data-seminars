import scala.math._

def count_words(filename: String, prefix: String, num: Int) {

  /* Define an RDD from a text file.  This RDD will represent the text as a
   * sequence of lines. */
  val lines_rdd = sc.textFile(filename)

  /* Define the set of all words starting with the giving prefix. */
  val words_rdd =
    lines_rdd
      .flatMap(line => line.split("[  ,;.…:–—?!«»()\\[\\]]"))
      .map(word => word.toLowerCase)
      .filter(word => !word.isEmpty && word.take(prefix.length) == prefix)

  /* Ask Spark to count the total number of words.  This will trigger a
   * computation. */
  val num_words = words_rdd.count

  /* Get the list of pairs (word, number_of_occurrences). */
  val stats: Array[(String, Int)] =
    words_rdd
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .collect

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

