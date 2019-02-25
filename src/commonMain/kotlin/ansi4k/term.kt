package ansi4k

sealed class Term<T: AnsiSequence>(sequenceFor: (StandardColor) -> T) {
    val black = sequenceFor(StandardColor.BLACK)
    val red = sequenceFor(StandardColor.RED)
    val green = sequenceFor(StandardColor.GREEN)
    val yellow = sequenceFor(StandardColor.YELLOW)
    val blue = sequenceFor(StandardColor.BLUE)
    val magenta = sequenceFor(StandardColor.MAGENTA)
    val cyan = sequenceFor(StandardColor.CYAN)
    val white = sequenceFor(StandardColor.WHITE)

    val brightBlack = sequenceFor(StandardColor.BRIGHT_BLACK)
    val brightRed = sequenceFor(StandardColor.BRIGHT_RED)
    val brightGreen = sequenceFor(StandardColor.BRIGHT_GREEN)
    val brightYellow = sequenceFor(StandardColor.BRIGHT_YELLOW)
    val brightBlue = sequenceFor(StandardColor.BRIGHT_BLUE)
    val brightMagenta = sequenceFor(StandardColor.BRIGHT_MAGENTA)
    val brightCyan = sequenceFor(StandardColor.BRIGHT_CYAN)
    val brightWhite = sequenceFor(StandardColor.BRIGHT_WHITE)

    val bold = AnsiBold
    val underline = AnsiUnderline
}
object Term16: Term<AnsiForeground16>(::AnsiForeground16)
object Term256: Term<AnsiForeground256>(::AnsiForeground256) {
    fun color(code: Int): AnsiForeground256 {
        check(code in 0..255) { "code must be between 0 and 255" }
        return AnsiForeground256(code)
    }
}