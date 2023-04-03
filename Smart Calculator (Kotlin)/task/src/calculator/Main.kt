package calculator

fun main() {

    while (true) {
        val input = readln().split(" ")
        if (input.first() == "/exit") break
        if (input.first() == "/help") {
            println("The program calculates the sum of numbers")
            continue
        }
        if (input.first().isBlank()) continue

        var sum = 0
        for (i in input) sum += i.toInt()

        println(sum)

    }
    println("Bye!")
}

