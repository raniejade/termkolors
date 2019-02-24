package ansi4k

interface Color
enum class StandardColor: Color {
    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    MAGENTA,
    CYAN,
    WHITE,

    BRIGHT_BLACK,
    BRIGHT_RED,
    BRIGHT_GREEN,
    BRIGHT_YELLOW,
    BRIGHT_BLUE,
    BRIGHT_MAGENTA,
    BRIGHT_CYAN,
    BRIGHT_WHITE;
}

sealed class AnsiSequence {
    protected abstract fun code(): String

    override fun toString(): String {
        return "\u001b[${code()}m"
    }
}

object AnsiReset: AnsiSequence() {
    override fun code() = "0"
}

sealed class AnsiColor(val foreground: Boolean): AnsiSequence()
class Ansi16(val color: StandardColor, foreground: Boolean): AnsiColor(foreground) {
    override fun code(): String {
        var code = when (color) {
            StandardColor.BLACK -> 30
            StandardColor.RED -> 31
            StandardColor.GREEN -> 32
            StandardColor.YELLOW -> 33
            StandardColor.BLUE -> 34
            StandardColor.MAGENTA -> 35
            StandardColor.CYAN -> 36
            StandardColor.WHITE -> 37
            StandardColor.BRIGHT_BLACK -> 90
            StandardColor.BRIGHT_RED -> 91
            StandardColor.BRIGHT_GREEN -> 92
            StandardColor.BRIGHT_YELLOW -> 93
            StandardColor.BRIGHT_BLUE -> 94
            StandardColor.BRIGHT_MAGENTA -> 95
            StandardColor.BRIGHT_CYAN -> 96
            StandardColor.BRIGHT_WHITE -> 97
        }


        if (!foreground) {
            code += 10
        }

        // 90 is start of the bright variants
        return if (code >= 90) {
            "$code;1"
        } else {
            "$code"
        }
    }

}
class Ansi256(val code: Int, foreground: Boolean): AnsiColor(foreground) {
    override fun code(): String {
        return when (foreground) {
            true -> "38;5;$code"
            false -> "48;5;$code"
        }
    }
}

object AnsiBold: AnsiSequence() {
    override fun code(): String {
        return "1"
    }
}

object AnsiUnderline: AnsiSequence() {
    override fun code(): String {
        return "4"
    }
}