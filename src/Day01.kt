import java.util.PriorityQueue

fun main() {
    val input = readInput("Day01")

    var now = 0
    var top = PriorityQueue<Int>(4)

    input.forEach {
        if (it.isNotEmpty()) {
            now += Integer.valueOf(it)
        }

        if (it.isEmpty()) {
            top.add(now)

            if (top.size > 3) {
                top.remove()
            }

            now = 0
        }
    }

    println("Part I: " + top.last())
    println("Part II: " + top.toList().sum())
}
