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

class RedirectableTerminal : ITerminal {
  var termImpl: ITerminal = NullTerminal

  override fun read() = termImpl.read()

  override fun bufferKey(c: Key) = termImpl.bufferKey(c)

  override fun resetInput() = termImpl.resetInput()

  override fun resetAttrib() = termImpl.resetAttrib()

  override fun put(x: Int, y: Int, ch: Char) = termImpl.put(x, y, ch)

  override fun get(x: Int, y: Int) = termImpl.get(x, y)

  override fun setBGCol(color: Int) = termImpl.setBGCol(color)

  override fun getBGCol(x: Int, y: Int) = termImpl.getBGCol(x, y)

  override fun setFGCol(color: Int) = termImpl.setFGCol(color)

  override fun getFGCol(x: Int, y: Int) = termImpl.getFGCol(x, y)

  override fun setHighlight(highlight: ColorPallette.Highlight) = termImpl.setHighlight(highlight)

  override fun getHighlight(x: Int, y: Int) = termImpl.getHighlight(x, y)

  override fun width() = termImpl.width()

  override fun height() = termImpl.height()

  override fun resize(x: Int, y: Int) = termImpl.resize(x, y)

  override fun clear() = termImpl.clear()

  override fun cursor() = termImpl.cursor()

  override fun cursor(value: Boolean) = termImpl.cursor(value)

  override fun cursorX() = termImpl.cursorX()

  override fun cursorY() = termImpl.cursorY()

  override fun cursorX(x: Int) = termImpl.cursorX(x)

  override fun cursorY(y: Int) = termImpl.cursorY(y)

  override fun scroll(n: Int) = termImpl.scroll(n)

  override fun scrollDown(n: Int) = termImpl.scrollDown(n)

  override fun scrollLeft(n: Int) = termImpl.scrollLeft(n)

  override fun scrollRight(n: Int) = termImpl.scrollRight(n)
}