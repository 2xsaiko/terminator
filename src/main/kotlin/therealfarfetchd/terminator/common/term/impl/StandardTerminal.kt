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

object StandardTerminal : ITerminal {
  private var keyBuffer = emptyList<Char>()
  private var width = 80
  private var height = 25
  private var displayMem = CharArray(80 * 25) { ' ' }

  override fun read(): Char? {
    return if (keyBuffer.isNotEmpty()) {
      val c = keyBuffer.first()
      keyBuffer = keyBuffer.drop(1)
      c
    } else {
      null
    }
  }

  override fun bufferKey(c: Char) {
    if (keyBuffer.size < 128) {
      keyBuffer += c
    }
  }

  override fun resetInput() {
    keyBuffer = emptyList()
  }

  override fun put(x: Int, y: Int, ch: Char) {
    if (x in 0 until width && y in 0 until height) {
      displayMem[x + y * width] = ch
    }
  }

  override fun get(x: Int, y: Int): Char? {
    return if (x in 0 until width && y in 0 until height) {
      displayMem[x + y * width]
    } else null
  }

  override fun width(): Int = width

  override fun height(): Int = height

  override fun resize(x: Int, y: Int) {
    if (width == x && height == y) return

    val displayMemNew = CharArray(x * y) {
      val dx = it % width
      val dy = it / width
      get(dx, dy) ?: ' '
    }

    displayMem = displayMemNew
    width = x
    height = y
  }

  override var cursor: Boolean = true
  override var cursorX: Int = 0
  override var cursorY: Int = 0

  override fun scroll(n: Int) {
    if (n < 1) return
    for (x in 0 until width) {
      for (y in 0 until height - n) {
        displayMem[x + y * width] = displayMem[x + (y + n) * width]
      }
    }
    for (x in 0 until width) {
      for (y in height - n until height) {
        displayMem[x + y * width] = ' '
      }
    }
  }

  override fun scrollDown(n: Int) {
    TODO("not implemented")
  }

  override fun scrollLeft(n: Int) {
    TODO("not implemented")
  }

  override fun scrollRight(n: Int) {
    TODO("not implemented")
  }
}