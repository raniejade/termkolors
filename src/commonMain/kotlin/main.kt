import ansi4k.Term256
import ansi4k.invoke
import ansi4k.plus

const val INDENT = "  "

fun main() {
    with(Term256) {
        println("# 256 colors")
        for (i in 0..15) {
            for (j in 0..15) {
                val code = i * 16 + j
                print(color(code)("$code".padStart(4)))
            }
            println()
        }
        println()

        val section = (color(255))
        val pass = color(76)
        val skipped = color(184)
        val failed = color(160)

        println("# Console reporter for a test framework")
        println((section + underline)("START"))
        println(INDENT + section("A calculator"))
        println(INDENT.repeat(2) + section("addition"))
        println(INDENT.repeat(3) + pass("\u2713$INDENT" + "1 + 2 == 3"))
        println(INDENT.repeat(3) + pass("\u2713$INDENT" + "1 + -1 == 0"))
        println((section + underline)("SUMMARY:"))
        println(pass("\u2713 2 tests completed"))
        println(skipped("\u2726 0 tests skipped"))
        println(failed("\u2717 0 tests failed"))
    }
}
