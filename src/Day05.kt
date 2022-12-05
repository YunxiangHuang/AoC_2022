import java.util.Scanner
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {
    val input = readInput("Day05")
    var matrix = HashMap<Int, ArrayList<Char>>(10)
    var rawMatrix = ArrayList<ArrayList<Char>>(10)

    // parse
    var actionBegin = 0
    for ((i, line) in input.withIndex()) {
        if (line.isEmpty()) {
            actionBegin = i + 1
            break
        }

        val num = (line.length + 1) / 4

        var chars = ArrayList<Char>(num)
        for (j in 0..num) {
            val idx = 4 * j + 1
            if (idx >= line.length) {
                break
            }

            chars.add(line[j * 4 + 1])
        }

        rawMatrix.add(chars)
    }

    // build matrix
    rawMatrix = rawMatrix.reversed() as ArrayList<ArrayList<Char>>
    //   line 0: id
    for (id in rawMatrix[0]) {
        matrix[Integer.valueOf(id.toString())] = ArrayList(rawMatrix.size)
    }

    for (line in rawMatrix.subList(1, rawMatrix.size)) {
        for ((id, c) in line.withIndex()) {
            if (!c.isLetter()) {
                continue
            }
            matrix[id + 1]!!.add(c)
        }
    }

    fun move(from: Int, to: Int) {
        matrix[to]!!.add(matrix[from]!!.removeLast())

    }

    fun moves(count: Int, from: Int, to: Int) {
        var tmp = ArrayList<Char>(count)

        for (i in 0 until count) { tmp.add(
            matrix[from]!!.removeLast()
        )}

        matrix[to]!!.addAll(tmp.reversed())
    }

    // Go action
    for (line in input.subList(actionBegin, input.size)) {
        var scanner = Scanner(line)
        var count = scanner.skip("move").nextInt()
        var from = scanner.skip(" from ").nextInt()
        var to = scanner.skip(" to ").nextInt()

//        while (count > 0) {
//            move(from, to); count--
//        }
        moves(count, from, to)
    }

    print("Part II: ")
    for (i in 1 .. matrix.size) {
        print("${matrix[i]!!.last()}")
    }
    println()
}
