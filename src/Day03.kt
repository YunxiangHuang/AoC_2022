import java.util.ArrayList

fun main() {
    val input = readInput("Day03")

    fun cal(c: Char): Int {
        if (c in 'a'..'z') {
            return c.code - 'a'.code + 1
        }

        if (c in 'A'..'Z') {
            return c.code - 'A'.code + 27
        }

        return 0
    }

    fun partOne() {
        var tp = 0
        for (rucksack in input) {
            var comp = HashMap<Char, Int>(rucksack.length / 2 + 1)

            for (c in rucksack.subSequence(0, rucksack.length / 2)) {
                comp[c] = comp.getOrDefault(c, 0) + 1
            }

            var total = 0
            var calculated = HashMap<Char, Boolean>(comp.size)
            for (c in rucksack.subSequence(rucksack.length / 2, rucksack.length)) {
                if (!comp.containsKey(c) || calculated.containsKey(c)) {
                    continue
                }

                calculated[c] = true
                total += cal(c)
            }

            tp += total
        }
        println("Part I: $tp")
    }

    fun partTwo() {

        var group = ArrayList<String>(3)
        var total = 0

        for (rucksack in input) {
            group.add(rucksack)
            if (group.size != 3) {
                continue
            }

            var badge = HashMap<Char, Boolean>(group[0].length)
            for (c in group[0]) {
                badge[c] = true
            }

            for (m in group.subList(1, group.size)) {
                var rm = HashMap<Char, Boolean>(badge.size)
                for (b in badge) {
                    if (!m.contains(b.key, false)) {
                        rm[b.key] = true
                    }
                }

                for (mutableEntry in rm) {
                    badge.remove(mutableEntry.key)
                }
            }

            for (b in badge) {
                total += cal(b.key)
            }

            group.clear()
        }

        println("Part II: $total")
    }

    partOne()
    partTwo()
}
