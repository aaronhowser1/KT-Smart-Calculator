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
    var input = input.filter { it != ' ' }

    //Get rid of repeated symbols
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