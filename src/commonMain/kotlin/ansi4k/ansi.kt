package ansi4k

enum class StandardColor {
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

abstract class AnsiSequence {
    abstract val sequence: String

    override fun toString(): String {
        return "\u001b[${sequence}m"
    }
}

internal class CompoundSequence(seq1: AnsiSequence, seq2: AnsiSequence): AnsiSequence() {
    override val sequence = "$seq1$seq2"
    override fun toString(): String {
        return sequence
    }
}

operator fun AnsiSequence.plus(other: AnsiSequence): AnsiSequence {
    return CompoundSequence(this, other)
}

operator fun AnsiSequence.invoke(text: String): String = "$this$text$AnsiReset"

object AnsiReset: AnsiSequence() {
    override val sequence = "0"
}

class AnsiForeground16(color: StandardColor): AnsiSequence() {
    override val sequence: String
    val bg by lazy { AnsiBackground16(color) }

    init {
        val code = when (color) {
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

        sequence = if (code >= 90) {
            "$code;1"
        } else {
            "$code"
        }
    }

    infix fun on(background: AnsiForeground16): AnsiSequence {
        return this + background.bg
    }
}

class AnsiBackground16(color: StandardColor): AnsiSequence() {
    override val sequence: String

    init {
        val code = when (color) {
            StandardColor.BLACK -> 40
            StandardColor.RED -> 41
            StandardColor.GREEN -> 42
            StandardColor.YELLOW -> 43
            StandardColor.BLUE -> 44
            StandardColor.MAGENTA -> 45
            StandardColor.CYAN -> 46
            StandardColor.WHITE -> 47
            StandardColor.BRIGHT_BLACK -> 100
            StandardColor.BRIGHT_RED -> 101
            StandardColor.BRIGHT_GREEN -> 102
            StandardColor.BRIGHT_YELLOW -> 103
            StandardColor.BRIGHT_BLUE -> 104
            StandardColor.BRIGHT_MAGENTA -> 105
            StandardColor.BRIGHT_CYAN -> 106
            StandardColor.BRIGHT_WHITE -> 107
        }

        sequence = if (code >= 100) {
            "$code;1"
        } else {
            "$code"
        }
    }
}

class AnsiForeground256(code: Int): AnsiSequence() {
    constructor(color: StandardColor): this(color.ordinal)
    override val sequence = "38;5;$code"
    val bg by lazy { AnsiBackground256(code) }

    infix fun on(background: AnsiForeground256): AnsiSequence {
        return this + background.bg
    }
}

class AnsiBackground256(code: Int): AnsiSequence() {
    constructor(color: StandardColor) : this(color.ordinal)
    override val sequence = "48;5;$code"
}

object AnsiBold: AnsiSequence() {
    override val sequence = "1"
}

object AnsiUnderline: AnsiSequence() {
    override val sequence = "4"
}