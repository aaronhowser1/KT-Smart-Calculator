package calculator

val knownVariables = mutableMapOf<String, Int>()

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

                handleInput(input)
            }
        }

        if (input == "/exit") break

    }

    println("Bye!")
}

fun handleInput(input: String) {
    try {

        val amountOfEquals = input.count {it == '='}
        val amountOfOperators = input.count {it == '+' || it == '-'}

        if (amountOfEquals > 0) {
            if (amountOfEquals == 1) {
                inputVariable(input)
                return
            } else {
                throw IllegalArgumentException()
            }
        }

        if (amountOfOperators > 0) {
            calculate(input)
            return
        }

        getVariable(input)

    } catch (exception: IllegalArgumentException) {
        println(exception.message)
    }
}

fun inputVariable(input: String) {

}

fun getVariable(input: String) {

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