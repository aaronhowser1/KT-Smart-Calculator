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

    for (group in input.split(' ')) {

        val amountMinus = group.count {it == '-'}
        val addingNext = amountMinus % 2 == 0

        val numberString = group.filter {it.isDigit()}

        if (numberString.isNotBlank()) {
            val number = numberString.toInt()
            if (addingNext) sum += number else sum -= number
        }
    }

    return sum
}