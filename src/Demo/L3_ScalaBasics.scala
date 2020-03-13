package Demo

object L3_ScalaBasics {

  def test_1 (): Unit = {
    println("Step 1: Using String interpolation to print a variable")
    val favoriteDonut: String = "Glazed Donut"
    println(s"My favorite donut = $favoriteDonut")

    println("\nStep 2: Using String interpolation on object properties")
    case class MySuperDonut(name: String, tasteLevel: String)
    val favoriteDonut2: MySuperDonut = MySuperDonut("Glazed Donut", "Very Tasty")
    println(s"My favorite donut name = ${favoriteDonut2.name}, tasteLevel = ${favoriteDonut2.tasteLevel}")

    println("\nStep 3: Using String interpolation to evaluate expressions")
    val qtyDonutsToBuy: Int = 10
    val qtyDonutsBought: Int = 10
    println(s"Did we buy as many donuts as planned = ${qtyDonutsToBuy == qtyDonutsBought}")

    println("\nStep 4: Using String interpolation for formatting text")
    val donutName: String = "Vanilla Donut"
    val donutTasteLevel: String = "Tasty"
    println(f"$donutName%23s $donutTasteLevel%10s")

    println("\nStep 5: Using f interpolation to format numbers")
    val donutPrice: Double = 2.50
    println(s"Donut price = $donutPrice")
    println(f"Formatted donut price = $donutPrice%.2f")

    println("\nStep 6: Using raw interpolation")
    println(raw"Favorite donut\t$donutName")
  }

  def test_2 (): Unit = {
    println("Step 1: Create a simple numeric range from 1 to 5 inclusive")
    val from1To5 = 1 to 5
    println(s"Range from 1 to 5 inclusive = $from1To5")

  }

}
