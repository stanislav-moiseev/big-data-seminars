# Big Data Seminars


### π estimation

<pre>
<b>cd pi-estimation/</b>
<b>spark-shell</b>

Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.3.0
      /_/

<b>scala> :load pi-estimation.scala</b>
<b>scala> spark_pi(1000)</b>

π ≈ 3.140000
</pre>


### Word counter for the _War and Peace_.

<pre>
<b>cd word-counter/</b>
<b>spark-shell</b>

<b>scala> :load "word-counter.scala"</b>
<b>scala> count_words("war-and-peace", "", 10)</b>

Here are 10/53,151 most popular words starting with '':

                   и   =>   4.65% (21,405 / 460,100)
                   в   =>   2.41% (11,092 / 460,100)
                  не   =>   1.90% (8,739 / 460,100)
                 что   =>   1.69% (7,791 / 460,100)
                  он   =>   1.63% (7,492 / 460,100)
                  на   =>   1.47% (6,781 / 460,100)
                   с   =>   1.29% (5,929 / 460,100)
                 как   =>   0.89% (4,110 / 460,100)
                 его   =>   0.86% (3,955 / 460,100)
                   к   =>   0.75% (3,467 / 460,100)

</pre>
