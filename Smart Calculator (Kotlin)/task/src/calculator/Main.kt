package calculator

fun main() {

    while (true) {
        val input = readln()
        if (input == "/exit") break
        if (input == "/help") {
            println("The program calculates the sum of numbers")
            continue
        }
        if (input.isBlank()) continue

        println(calculate(input))

    }
    println("Bye!")
}

fun calculate(input: String): Int {

    var sum = 0

    val split = input.split(Regex("-|\\+"))

    println(split)

    return sum
}