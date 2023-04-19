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
            println(calculate(filteredInput))
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

    for (group in split) {

        if (group.any {it.isLetter()}) {
            //Since group could be "+a" rather than just "a"

            var variable = group

            val isSubtracting = group.first() == '-'
            if (isSubtracting || group.first() == '+') {
                variable = variable.drop(1)
            }

            if (isSubtracting) {
                sum -= getVariable(variable)
            } else {
                sum += getVariable(variable)
            }
        } else {
            sum += group.toInt()
        }

    }

    return sum
}

fun filterInput(input: String): String {
    var filteredInput = input.filter {it != ' '}

    //Check to make sure that, if there are any characters, they are valid saved variables
    val splitInput = input.split(Regex("-|\\+"))
    for (group in splitInput) {
        if (group.any { it.isLetter() }) {
            getVariable(group)
            //If the above didn't throw an exception, the group is good, and it can continue
            continue
        }

        //Might not be needed, as it would break above
        if (group.any { !it.isLetterOrDigit() && it != '+' && it != '-'}) {
            throw (IllegalArgumentException("Invalid expression"))
        }
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

    if (!filteredInput.last().isLetterOrDigit()) throw (IllegalArgumentException("Invalid expression"))

    return filteredInput
}