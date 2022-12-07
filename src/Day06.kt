
fun main() {
    val lines = readInput("Day06")
    val dLen = 14

    for (l in lines) {
        var flag = HashMap<Char, Int>(dLen)

        for ((i, c) in l.withIndex() ) {
            flag[c] = i
            if(flag.size >= dLen) {
                println("${i+1}")
                break
            }
            if (i > dLen){
                flag.remove(l[i-dLen+1], i-dLen+1)
            }
        }
    }
}