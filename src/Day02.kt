class shap(s: String) {
    private val mark = {
        when (s) {
            "A", "X" -> {
                "Rock"
            }

            "B", "Y" -> {
                "Paper"
            }

            else -> {
                "Scissors"
            }
        }
    };

    private val score = {
        when (s) {
            "A", "X" -> {
                1
            }

            "B", "Y" -> {
                2
            }

            else -> {
                3
            }
        }
    };

    fun cal(other: shap): Int {
        if (mark() == other.mark()) {
            return 3 + score()
        }

        // Win cases
        if ((mark() == "Rock" && other.mark() == "Scissors") ||
            (mark() == "Scissors" && other.mark() == "Paper") ||
            (mark() == "Paper" && other.mark() == "Rock")
        ) {
            return 6 + score()
        }

        return score()
    }

    fun how2Lose(): shap {
        return when (mark()) {
            "Rock" -> {
                shap("C")
            }

            "Scissors" -> {
                shap("B")
            }

            else -> shap("A")
        }
    }

    fun how2Draw(): shap {
        return this
    }

    fun how2Win(): shap {
        return when (mark()) {
            "Rock" -> {
                shap("B")
            }

            "Scissors" -> {
                shap("A")
            }

            else -> {
                shap("C")
            }
        }
    }

}

fun partOne(lines: List<String>) {
    var total = 0

    for (l in lines) {
        val line = l.split(" ")
        val other = shap(line[0])
        val me = shap(line[1])

        total += me.cal(other)
    }

    println("Part I: $total")
}

fun partTwo(lines: List<String>) {
    var total = 0

    for (l in lines) {
        var line = l.split(" ")
        var other = shap(line[0])
        var me = {
            when(line[1]) {
                "X" ->{ other.how2Lose()}
                "Y" ->{other.how2Draw()}
                else ->{other.how2Win()}
            }
        }

        total += me().cal(other)
    }

    println("Part II: $total")
}

fun main() {
    val input = readInput("Day02")
    partOne(input)
    partTwo(input)
}
