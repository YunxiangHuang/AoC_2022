fun main() {
    class Range(r: String) {
        private val sepIdx = r.indexOf('-')
        private var begin = kotlin.run {
            Integer.valueOf(r.subSequence(0, sepIdx).toString())
        }
        private var end = kotlin.run {
            Integer.valueOf(r.subSequence(sepIdx + 1, r.length).toString())
        }

        fun contains(other: Range): Boolean {
            return this.begin <= other.begin &&
                    this.end >= other.end
        }

        fun overlap(other: Range): Boolean {
            return other.begin in this.begin..this.end ||
                    other.end in this.begin..this.end
        }
    }

    val input = readInput("Day04")

    var containTotal = 0
    var overlapTotal = 0
    for (line in input) {
        val slices = line.split(',')
        val rangeA = Range(slices[0])
        val rangeB = Range(slices[1])

        if (rangeA.contains(rangeB) || rangeB.contains(rangeA)) {
            containTotal++
        }
        if (rangeA.overlap(rangeB) || rangeB.overlap(rangeA)) {
            overlapTotal++
        }
    }

    println("Part I: $containTotal")
    println("Part II: $overlapTotal")
}
