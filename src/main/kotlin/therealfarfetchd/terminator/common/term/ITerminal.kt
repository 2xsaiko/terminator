package therealfarfetchd.terminator.common.term

interface ITerminal {
  /**
   * Non-blocking. If a char is available to be read, return it, otherwise return null.
   */
  fun read(): Char?

  /**
   * Clears the keyboard input queue
   */
  fun resetInput()

  /**
   * Writes a char at the specified position. If this is out of bounds, does nothing.
   */
  fun put(x: Int, y: Int, ch: Char)

  /**
   * Returns the width of the terminal (in chars)
   */
  fun width(): Int

  /**
   * Returns the height of the terminal (in chars)
   */
  fun height(): Int

  /**
   * True if the cursor should be displayed.
   */
  var cursor: Boolean

  /**
   * Scrolls the terminal down by n characters.
   */
  fun scroll(n: Int = 1)

  /**
   * Scrolls the terminal upward by n characters.
   */
  fun scrollUp(n: Int = 1)

  /**
   * Scrolls the terminal left by n characters.
   */
  fun scrollLeft(n: Int = 1)

  /**
   * Scrolls the terminal right by n characters.
   */
  fun scrollRight(n: Int = 1)
}