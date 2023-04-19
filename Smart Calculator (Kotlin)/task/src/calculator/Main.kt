package calculator

fun main() {

    while (true) {
        val input = readln()

        when (input) {
            "/exit" -> break
            "/help" -> println("The program calculates the sum of numbers")
            else -> {

                if (input.isEmpty()) continue
                if (input.first() == '/') {
                    println("Unknown command")
                    continue
                }

                try {
                    println(calculate(input))
                } catch (exception: Exception) {
                    println(exception.message)
                }
            }
        }
    }

    println("Bye!")
}

fun calculate(input: String): Int {
    var input = filterInput(input)

    //Get ready to split
    input = input.replace("+"," +")
    input = input.replace("-", " -")

    val split = input.split(' ').filter { it.isNotBlank() }

    var sum = 0

    for (number in split) {
        sum += number.toInt()
    }

    return sum
}

fun filterInput(input: String): String {
    var input = input.filter {it != ' '}

    if (input.any { !it.isDigit() && it != '+' && it != '-'}) {
        throw (IllegalArgumentException("Invalid expression"))
    }

    while (input.contains("--")) {
        input = input.replace("--","+")
    }
    while (input.contains("++")) {
        input = input.replace("++","+")
    }
    while (input.contains("+-") || input.contains("-+")) {
        input = input.replace("+-","-")
        input = input.replace("-+","-")
    }

    if (!input.last().isDigit()) throw (IllegalArgumentException("Invalid expression"))

    return input
}