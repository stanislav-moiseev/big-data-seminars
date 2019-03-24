# Big Data Seminars


### π estimation

<pre>
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


### Word counter

<pre>
<b>spark-shell</b>

<b>scala> :load word-counter.scala</b>
<b>scala> count_words("data/war-and-peace", "", 10)</b>

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


### Next word prediction

<pre>
<b>spark-shell</b>

<b>scala> :load next-word-prediction.scala </b>
<b>scala>  </b>

<b>scala> predict_word("data/war-and-peace", List("однажды"), 1)</b>
однажды она не было что он не было что он не было что он не было ...

<b>scala> predict_word("data/war-and-peace", List("однажды"), 2)</b>
однажды она пришла к княжне марье и покойнике ее отце которого видимо не желая расстаться с этим человеком и не мог понять того что он не мог понять того ...

<b>scala> predict_word("data/war-and-peace", List("однажды"), 3)</b>
однажды она пришла к графине хотела что‑то сказать но пьер перебил его 

<b>scala> predict_word("data/war-and-peace", List("однажды"), 4)</b>
однажды она пришла к графине хотела что‑то сказать ей и вдруг заплакала 

</pre/


### Connected component of a graph containing a given graph node

<pre>
<b>spark-shell</b>

<b>scala> :load connected-component.scala </b>
<b>scala> ccomp("data/graphs/0.txt", 16) </b>

16 
33 34 
3 9 15 19 21 23 24 30 31 32 10 14 20 27 28 29 
1 2 4 8 26 25 
5 6 7 11 12 13 18 22 
17 

</pre>
