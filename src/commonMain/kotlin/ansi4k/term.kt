package ansi4k

class StyledText(val text: String, val styles: List<AnsiSequence> = emptyList()) {
    override fun toString(): String {
        return if (styles.isNotEmpty()) {
            "${styles.joinToString(separator = "")}$text$AnsiReset"
        } else {
            text
        }
    }
}

typealias Style = StyleParams.() -> Unit
class StyleParams {
    var bold: Boolean = false
    var underlined: Boolean = false
    var foreground: Color? = null
    var background: Color? = null
}

sealed class Term {
    fun String.styled(block: Style): StyledText {
        val params = StyleParams()
        params.block()
        val styles = mutableListOf<AnsiSequence>()

        params.run {
            foreground?.let {
                styles.add(toAnsiColor(it, true))
            }

            background?.let {
                styles.add(toAnsiColor(it, false))
            }

            if (bold) {
                styles.add(AnsiBold)
            }

            if (underlined) {
                styles.add(AnsiUnderline)
            }
        }

        return StyledText(this, styles.toList())
    }

    fun style(block: Style): Style {
        return block
    }

    protected abstract fun toAnsiColor(color: Color, foreground: Boolean): AnsiColor
}

object Term16: Term() {
    override fun toAnsiColor(color: Color, foreground: Boolean): AnsiColor {
        if (color !is StandardColor) {
            throw UnsupportedOperationException("Unsupported color: $color")
        }

        return Ansi16(color, foreground)
    }

}

fun main() {
    with(Term16) {
        val myStyle = style {
            foreground = StandardColor.CYAN
            bold = true
            underlined = true
        }

        val a = "Hello world".styled(myStyle)
        println(a)
    }
}