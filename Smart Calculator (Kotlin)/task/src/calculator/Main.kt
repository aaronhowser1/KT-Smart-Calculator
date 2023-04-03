package calculator

fun main() {

    val input = readln().split(' ').map {it.toInt()}
    val first = input[0]
    val second = input[1]
    println((first+second))
}
