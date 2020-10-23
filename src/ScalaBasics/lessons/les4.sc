

// 3. generic-классы
class Queue[A] {
  private var elements: List[A] = Nil
  def push(x: A) { elements = elements :: List(x) }
  def head: Option[A] = {
    if (elements.isEmpty) None
    else Some(elements.head)
  }
  def pop: Option[A] = {
    val currentHead = head
    if (!currentHead.isEmpty) {
      elements = elements.tail
    }
    currentHead
  }
}

val que_i = new Queue[Int]
que_i.push(3)
que_i.push(1)
que_i.push(4)
que_i.push(1)
que_i.push(5)
que_i.push(9)
while (!que_i.head.isEmpty)
  println(que_i.pop.get)

trait Animal
class Lion extends Animal
class Wolf extends Animal
val que_a = new Queue[Animal]
que_a.push(new Lion)
que_a.push(new Lion)
que_a.push(new Lion)
que_a.push(new Wolf)
while (!que_a.head.isEmpty)
  println(que_a.pop.get)


