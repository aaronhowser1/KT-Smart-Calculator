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

        val filteredInput = input.filter {it != ' '}

        val amountOfEquals = filteredInput.count {it == '='}
        val amountOfOperators = filteredInput.count {it == '+' || it == '-'}

        if (amountOfEquals > 0) {
            if (amountOfEquals == 1) {

                val split = filteredInput.split('=')
                val variableName = filterVariableName(split[0])
                val variableValue = split[1]

                setVariable(variableName, variableValue)
                return
            } else {
                throw IllegalArgumentException("Invalid assignment")
            }
        }

        if (amountOfOperators > 0) {
            calculate(filteredInput)
            return
        }

        println(getVariable(filteredInput))

    } catch (exception: IllegalArgumentException) {
        println(exception.message)
    }
}

fun setVariable(name: String, value: String) {

    val valueIsNumber = value.all {it.isDigit()}

    if (valueIsNumber) {
        knownVariables[name] = value.toInt()
    } else {
        knownVariables[name] = getVariable(filterVariableValue(value))
    }
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

fun filterVariableValue(input: String): String {
    if (input.any { it !in 'a'..'z' && it !in 'A'..'Z' }) {
        throw IllegalArgumentException("Invalid assignment")
    }

    return input
}

fun calculate(input: String): Int {
    var filteredInput = filterInput(input)

    //Get ready to split
    filteredInput = filteredInput.replace("+"," +")
    filteredInput = filteredInput.replace("-", " -")

    val split = filteredInput.split(' ').filter { it.isNotBlank() }

    var sum = 0

    for (number in split) {
        sum += number.toInt()
    }

    return sum
}

fun filterInput(input: String): String {
    var filteredInput = input.filter {it != ' '}

    if (filteredInput.any { !it.isDigit() && it != '+' && it != '-'}) {
        throw (IllegalArgumentException("Invalid expression"))
    }

    while (filteredInput.contains("--")) {
        filteredInput = filteredInput.replace("--","+")
    }
    while (filteredInput.contains("++")) {
        filteredInput = filteredInput.replace("++","+")
    }
    while (filteredInput.contains("+-") || filteredInput.contains("-+")) {
        filteredInput = filteredInput.replace("+-","-")
        filteredInput = filteredInput.replace("-+","-")
    }

    if (!filteredInput.last().isDigit()) throw (IllegalArgumentException("Invalid expression"))

    return filteredInput
}