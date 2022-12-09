fun main() {
    fun calDistance(head: Pair<Int, Int>, tail: Pair<Int, Int>): Int {
        return kotlin.math.abs(head.first - tail.first) +
                kotlin.math.abs(head.second - tail.second)
    }

    fun needMove(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
        if (head.first != tail.first && head.second != tail.second) {
            return calDistance(head, tail) > 2
        }

        return calDistance(head, tail) > 1
    }

    fun follow(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        if (!needMove(head, tail)) {
            return tail
        }

        // in same row
        if (head.first == tail.first && head.second != tail.second) {
            if (head.second > tail.second) {
                return Pair(tail.first, tail.second + 1)
            }
            return Pair(tail.first, tail.second - 1)
        }

        // in same col
        if (head.first != tail.first && head.second == tail.second) {
            if (head.first > tail.first) {
                return Pair(tail.first + 1, tail.second)
            }
            return Pair(tail.first - 1, tail.second)
        }

        var df = -1
        var ds = -1

        if (head.first > tail.first) {
            df = 1
        }
        if (head.second > tail.second) {
            ds = 1
        }
        return Pair(tail.first + df, tail.second + ds)
    }

    val size = 10
    val nodes = ArrayList<Pair<Int, Int>>(size)
    val flags = HashMap<Pair<Int, Int>, Boolean>()

    for (i in 0 until size) {
        nodes.add(Pair(0, 0))
    }

    for (cmd in readInput("Day09")) {
        val l = cmd.split(" ")
        val direct = l[0]
        val steps = l[1].toInt()

        for (step in 0 until steps) {
            var head = nodes[0]

            when (direct) {
                "L" -> {
                    head = Pair(head.first - 1, head.second)
                }

                "R" -> {
                    head = Pair(head.first + 1, head.second)
                }

                "U" -> {
                    head = Pair(head.first, head.second + 1)
                }

                "D" -> {
                    head = Pair(head.first, head.second - 1)
                }
            }

            nodes[0] = head

            flags[nodes[size - 1]] = true
            for (i in 1 until size) {
                nodes[i] = follow(nodes[i - 1], nodes[i])
            }
            flags[nodes[size - 1]] = true
        }
    }
    println("Part I: ${flags.size}")
}