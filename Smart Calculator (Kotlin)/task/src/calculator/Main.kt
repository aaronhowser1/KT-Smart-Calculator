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
    var addingNext = true

    for (i in input.indices) {

        val char = input[i]
        if (char == ' ') continue
        if (char == '-') {
            addingNext = !addingNext
            continue
        }

        if (char.isDigit()) {
            if (addingNext) sum += char.digitToInt() else sum -= char.digitToInt()
            addingNext = true
        }

    }

    return sum
}