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

    }

    println("Bye!")
}

fun handleInput(input: String) {
    try {

        val input = input.filter {it != ' '}

        val amountOfEquals = input.count {it == '='}
        val amountOfOperators = input.count {it == '+' || it == '-'}

        if (amountOfEquals > 0) {
            if (amountOfEquals == 1) {

                val split = input.split('=')
                val variableName = filterVariableName(split[0])
                val variableValue = split[1].toInt()

                setVariable(variableName, variableValue)
                return
            } else {
                throw IllegalArgumentException("Too many equal signs")
            }
        }

        if (amountOfOperators > 0) {
            calculate(input)
            return
        }

        println(getVariable(input))

    } catch (exception: IllegalArgumentException) {
        println(exception.message)
    }
}

fun setVariable(name: String, value: Int) {
    knownVariables[name] = value
}

fun getVariable(name: String): Int {
    val value = knownVariables[filterVariableName(name)] ?: throw IllegalArgumentException("Unknown variable")

    return value
}

fun filterVariableName(input: String): String {
    if (input.any { it !in 'a'..'z' && it !in 'A'..'Z' }) {
        throw IllegalArgumentException("Invalid identifier")
    }

    return input
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