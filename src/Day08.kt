import kotlin.math.max

fun main() {
    class Node(h: Char) {
        var up = -1;
        var down = -1;
        var left = -1;
        var right = -1;

        val height = h.toString().toInt();

        fun isVisiable(): Boolean {
            return height > up || height > down || height > left || height > right
        }
    }


    var map = ArrayList<ArrayList<Node>>()
    for (line in readInput("Day08")) {
        var row = ArrayList<Node>(line.length)
        for (h in line) {
            row.add(Node(h))
        }
        map.add(row)
    }

    fun partOne(): Int {
        // Update highest.
        for ((i, row) in map.withIndex()) {
            // update left
            for ((j, node) in row.withIndex()) {
                if (j == 0) {
                    continue
                }
                node.left = max(row[j - 1].height, row[j - 1].left)
            }
            // update right
            var j = row.size - 2
            while (j >= 0) {
                row[j].right = max(row[j + 1].height, row[j + 1].right)
                j--
            }

            // update up
            if (i == 0) {
                continue
            }
            for ((j, node) in row.withIndex()) {
                node.up = max(map[i - 1][j].height, map[i - 1][j].up)
            }
        }
        // update down
        var i = map.size - 2
        while (i >= 0) {
            for ((j, node) in map[i].withIndex()) {
                node.down = max(map[i + 1][j].height, map[i + 1][j].down)
            }
            i--
        }

        var total = 0
        for ((i, row) in map.withIndex()) {
            for ((j, col) in row.withIndex()) {
                if (col.isVisiable()) {
                    total++
                    continue
                }
            }
        }
        return total
    }

    fun partTwo(): Int {
        fun cal(i: Int, j: Int): Int {
            var left = 0
            var right = 0
            var up = 0
            var down = 0

            val base = map[i][j].height

            var ti = i - 1
            while (ti >= 0) {
                up++
                if (map[ti][j].height >= base) {
                    break
                }
                ti--
            }

            ti = i + 1
            while (ti < map.size) {
                down++
                if (map[ti][j].height >= base) {
                    break
                }
                ti++
            }

            var tj = j - 1
            while (tj >= 0) {
                left++
                if (map[i][tj].height >= base) {
                    break
                }
                tj--
            }

            tj = j + 1
            while (tj < map[i].size) {
                right++
                if (map[i][tj].height >= base) {
                    break
                }
                tj++

            }
//            println("[$i, $j]: $left, $right, $up, $down")
            return left * right * up * down
        }

        var res = 0
        for (i in 0 until map.size - 1) {
            for (j in 0 until map[i].size - 1) {
                res = max(res, cal(i, j))
            }
        }

        return res
    }

    println("Part I: ${partOne()}")
    println("Part II: ${partTwo()}")
}