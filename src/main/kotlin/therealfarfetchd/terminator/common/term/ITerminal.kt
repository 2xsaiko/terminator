/*
 * Copyright (c) 2017 Marco Rebhan (the_real_farfetchd)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package therealfarfetchd.terminator.common.term

interface ITerminal {
  /**
   * Non-blocking. If a char is available to be read, return it, otherwise return null.
   */
  fun read(): Char?

  /**
   * Puts a key in the key buffer.
   */
  fun bufferKey(c: Char)

  /**
   * Clears the keyboard input queue
   */
  fun resetInput()

  /**
   * Writes a char at the specified position. If this is out of bounds, does nothing.
   */
  fun put(x: Int, y: Int, ch: Char)

  /**
   * Gets the char at the specified position. Returns null if it's out of bounds
   */
  fun get(x: Int, y: Int): Char?

  /**
   * Returns the width of the terminal (in chars)
   */
  fun width(): Int

  /**
   * Returns the height of the terminal (in chars)
   */
  fun height(): Int

  /**
   * Set the size of the terminal (in chars)
   */
  fun resize(x: Int, y: Int)

  /**
   * True if the cursor should be displayed.
   */
  var cursor: Boolean

  /**
   * The cursor x position
   */
  var cursorX: Int

  /**
   * The cursor y position
   */
  var cursorY: Int

  /**
   * Scrolls the terminal up by n characters.
   */
  fun scroll(n: Int = 1)

  /**
   * Scrolls the terminal down by n characters.
   */
  fun scrollDown(n: Int = 1)

  /**
   * Scrolls the terminal left by n characters.
   */
  fun scrollLeft(n: Int = 1)

  /**
   * Scrolls the terminal right by n characters.
   */
  fun scrollRight(n: Int = 1)
}