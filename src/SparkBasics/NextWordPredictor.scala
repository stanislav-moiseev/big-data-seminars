package SparkBasics

import org.apache.spark.SparkContext
import org.apache.spark.rdd._

import scala.math._
import collection.mutable.Map

object NextWordPredictor {

  /* This function finds all subsequences of 'seq' starting with a given prefix
   * and returns the list of words coming right after the prefix.  Words may be
   * duplicated in the output.
   */
  def continuations(seq: Array[String], prefix: List[String]): List[String] = {
    var str: List[String]   = seq.toList
    var conts: List[String] = List()

    while(str.length >= prefix.length + 1) {

      if(str.take(prefix.length) == prefix) {
        conts = str(prefix.length) +: conts
      }
      str = str.tail
    }
    return conts
  }


  /* This function takes a list of words ('prefix') and recursively predicts the
   * next word based on 'num' previous words in the sentence.  The statistics is
   * computed based on the text from 'filename'.
   *
   * 'filename'   Name of file or directory with graph edges.  In case of a
   *              directory, all files from that directory will be loaded.
   *
   * 'prefix'     A list of strings to start the sequence.
   *
   * 'num'        The parameter of next word prediction algorithm.  The next word
   *              will be predicted based on 'num' previous words (or based on all
   *              words of the current sentence if it contains less than 'num'
   *              words).
   */
  def predict_word(sc: SparkContext, filename: String, prefix_in: List[String], num: Int) {

    val sentences_rdd: RDD[Array[String]] = sc
      /* Define an RDD from a text file.  This RDD will represent the text as a
       * sequence of lines. */
      .textFile(filename)

      /* Convert to lower case. */
      .map(_.toLowerCase)

      /* Split every line into sentences separated by the period character ".",
       * dots "…", "?", or "!". */
      .flatMap(line => line.split("[.…?!]"))

      /* Map every sentence into a sequence of words.  The regexp defines the set
       * of word separating characters.  This might generate empty words. */
      .map(sentence => sentence.split("[  ,;:–—«»()\\[\\]]")
        .filter(word => !word.isEmpty))

    val prefix = prefix_in.map( _.toLowerCase )
    printf("%s", prefix.foldLeft("")((s, x) => s ++ x ++ " "))

    var current_prefix = prefix.take(num)

    while(!current_prefix.isEmpty) {
      /* The (multi)set of all words coming after the word sequence 'current_prefix'
       * somewhere in the text. */
      val words_rdd = sentences_rdd
        .filter(_.length >= prefix.length + 1)
        .flatMap(seq => continuations(seq, current_prefix))

      /* 'stats' is a list of pairs '(w, c)' where 'w' is a word from 'words_rdd'
       * and 'c' is the number of occurrences of 'w' in 'words_rdd'.*/
      val stats: Iterable[(String, Int)] = words_rdd
        .map(word => (word, 1))
        .reduceByKey(_ + _)
        .collect

      //println()
      //println("BREAK POINT #1: press any key ...")
      //val ch = scala.io.StdIn.readLine()

      if(!stats.isEmpty) {

        // option 1 - take most probable word
//        val next_word = stats.maxBy(_._2)._1

        // option 2 - take random word among most probable 3 words
        val k = 3
        val most_probable_k_word = stats.toList.sortWith(_._2 > _._2 ).take(k)
        val rand_idx = scala.util.Random.nextInt( most_probable_k_word.length )
        val next_word =  most_probable_k_word( rand_idx )._1

        printf("%s ", next_word)

        current_prefix = current_prefix ++ List(next_word)

        if(current_prefix.length == num + 1) {
          current_prefix = current_prefix.drop(1)
        }

      } else {
        current_prefix = List()
      }
    }
  }

  def isPalindrome(w: String): Boolean = {
    return (w == w.reverse)
  }

  def searchForPalindromes(sc: SparkContext, filename: String, min_len : Int) {

    val palindromes_rdd: RDD[String] = sc
      .textFile(filename)
      .map(_.toLowerCase)
      .flatMap(line => line.split("[.…?!]"))
      .flatMap(sentence => sentence.split("[  ,;:–—«»()\\[\\]]"))
      .filter( word => (!word.isEmpty)&&(word.length >= min_len)  )
      .distinct()
      .filter( word => isPalindrome(word) )

    val palindrs = palindromes_rdd.collect().sorted
    palindrs.foreach( s => println(s"$s") )
  }

}
