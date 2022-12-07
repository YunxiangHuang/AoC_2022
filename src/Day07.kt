import java.util.Arrays
import java.util.Scanner
import kotlin.math.max
import kotlin.math.min
import kotlin.system.exitProcess

class RootDirectory {
    enum class Type {
        Directory, File
    }

    interface Node : Comparable<Node> {
        fun getName(): String
        fun getType(): Type
        fun getSize(): Int

        override operator fun compareTo(other: Node): Int {
            return getSize().compareTo(other.getSize())
        }
    }

    private class Directory(parent: Directory?, name: String) : Node {
        private var name = name
        private var member = HashMap<String, Node>()
        private var size = 0
        private val parent = parent

        override fun getName(): String {
            return name
        }

        fun getParent(): Directory {
            if (parent == null) {
                return this
            }
            return parent
        }

        override fun getType(): Type {
            return Type.Directory
        }

        override fun getSize(): Int {
            return size
        }

        fun isRoot(): Boolean {
            return parent == null
        }

        fun create(l: String) {
            val elements = l.split(" ")
            when (elements[0]) {
                "dir" -> {
                    this.createDirectory(Directory(this, elements[1]))
                }

                else -> {
                    this.createFile(File(elements[1], Integer.valueOf(elements[0])))
                }
            }
        }

        fun createFile(f: File) {
            member[f.getName()] = f

            var now = this
            while (true) {
                now.size += f.getSize()

                if (now.isRoot()) {
                    break
                }
                now = now.getParent()
            }
        }

        fun createDirectory(d: Directory) {
            member[d.getName()] = d

            if (d.getSize() == 0) {
                return
            }

            var now = this
            while (true) {
                now.size += d.getSize()

                if (now.isRoot()) {
                    break
                }

                now = now.getParent()
            }
        }

        fun cd(name: String): Directory {
            return member[name] as Directory
        }

        fun listDir(maxSize: Int): List<Node> {
            var list = ArrayList<Node>(member.size)

            if (getSize() <= maxSize) {
                list.add(this)
            }

            for (m in member.values) {
                if (m.getType() != Type.Directory) {
                    continue
                }

                m as Directory
                list.addAll(m.listDir(maxSize))
            }

            return list
        }
    }

    private class File(name: String, size: Int) : Node {
        private var name = name
        private var size = size

        override fun getName(): String {
            return name
        }

        override fun getSize(): Int {
            return size
        }

        override fun getType(): Type {
            return Type.File
        }
    }

    private val root = Directory(null, "/")
    private var cur = root

    fun getSize(): Int {
        return root.getSize()
    }

    fun cd(name: String) {
        cur = when (name) {
            "/" -> {
                root
            }

            ".." -> {
                cur.getParent()
            }

            else -> {
                cur.cd(name)
            }
        }
    }

    fun create(lines: List<String>) {
        lines.forEach { cur.create(it) }
    }

    fun listDir(maxSize: Int): List<Node> {
        return root.listDir(maxSize)
    }
}

fun main() {
    fun cmdParser(input: List<String>): List<Pair<String, String>> {
        var cmds = ArrayList<Pair<String, String>>()
        var scan = Scanner(input.joinToString("\n"))

        scan.next() // skip the first '$'
        while (scan.hasNext()) {
            var c = scan.next()
            when (c) { // read command
                "cd" -> {
                    cmds.add(Pair("cd", scan.next()))
                }

                "ls" -> {
                    var body = ArrayList<String>()

                    while (scan.hasNext()) {
                        val typ = scan.next()
                        if (typ == "$") {
                            break
                        }
                        body.add("$typ ${scan.nextLine().trim()}")
                    }

                    cmds.add(Pair("ls", body.joinToString("\n")))
                }
            }
        }

        return cmds
    }

    var root = RootDirectory()
    for (cmd in cmdParser(readInput("Day07"))) {
        when (cmd.first) {
            "cd" -> {
                root.cd(cmd.second)
            }

            "ls" -> {
                root.create(cmd.second.split("\n"))
            }
        }
    }


    // Part I
    println("Part I: ${root.listDir(100000).sumOf { it.getSize() }}")

    // Part II
    val all = 70000000
    val free = all - root.getSize()
    val need = 30000000 - free

    root.listDir(root.getSize() + 1).sorted().forEach {
        if (it.getSize() >= need) {
            println("Part II: ${it.getSize()}")
            return
        }
    }
}

