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

import therealfarfetchd.terminator.common.term.ITerminal

@Suppress("RedundantSetter")
object NullTerminal : ITerminal {
  override fun read(): Char? = null

  override fun bufferKey(c: Char) {}

  override fun resetInput() {}

  override fun put(x: Int, y: Int, ch: Char) {}

  override fun get(x: Int, y: Int): Char? = if (y == 0 && x in 0..14) "Hello world! :P"[x] else null

  override fun width(): Int = 80

  override fun height(): Int = 25

  override fun resize(x: Int, y: Int) {}

  override var cursor: Boolean
    get() = true
    set(value) {}

  override var cursorX: Int
    get() = 0
    set(value) {}

  override var cursorY: Int
    get() = 0
    set(value) {}

  override fun scroll(n: Int) {}

  override fun scrollDown(n: Int) {}

  override fun scrollLeft(n: Int) {}

  override fun scrollRight(n: Int) {}
}