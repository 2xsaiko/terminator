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

package therealfarfetchd.terminator.common.term.impl

import therealfarfetchd.terminator.common.term.ColorPallette
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.Key

object NullTerminal : ITerminal {
  override fun resetAttrib() {}

  override fun setBGCol(color: Int) {}

  override fun getBGCol(x: Int, y: Int): Int? = 0

  override fun setFGCol(color: Int) {}

  override fun getFGCol(x: Int, y: Int): Int? = 1

  override fun setHighlight(highlight: ColorPallette.Highlight) {}

  override fun getHighlight(x: Int, y: Int): ColorPallette.Highlight? = ColorPallette.Highlight.Normal

  override fun clear() {}

  override fun cursor() = true

  override fun cursor(value: Boolean) {}

  override fun cursorX() = 22

  override fun cursorY() = 0

  override fun cursorX(x: Int) {}

  override fun cursorY(y: Int) {}

  override fun read() = null

  override fun bufferKey(c: Key) {}

  override fun resetInput() {}

  override fun put(x: Int, y: Int, ch: Char) {}

  override fun get(x: Int, y: Int) = if (y == 0 && x in 0..21) "Terminal disconnected."[x] else null

  override fun width() = 80

  override fun height() = 25

  override fun resize(x: Int, y: Int) {}

  override fun scroll(n: Int) {}

  override fun scrollDown(n: Int) {}

  override fun scrollLeft(n: Int) {}

  override fun scrollRight(n: Int) {}
}