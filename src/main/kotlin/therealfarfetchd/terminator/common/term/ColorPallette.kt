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

class ColorPallette {
  private val colors = IntArray(24) {
    // Taken from Konsole.
    when (it) {
    // Normal
      0 -> 0x000000
      1 -> 0xB21818
      2 -> 0x18B218
      3 -> 0xB26818
      4 -> 0x1818B2
      5 -> 0xB218B2
      6 -> 0x18B2B2
      7 -> 0xB2B2B2

    // Light
      8 -> 0x686868
      9 -> 0xFF5454
      10 -> 0x54FF54
      11 -> 0xFFFF54
      12 -> 0x5454FF
      13 -> 0xFF54FF
      14 -> 0x54FFFF
      15 -> 0xFFFFFF

    // Dark
      16 -> 0x181818
      17 -> 0x650000
      18 -> 0x006500
      19 -> 0x655E00
      20 -> 0x000065
      21 -> 0x650065
      22 -> 0x006565
      23 -> 0x656565

      else -> 0
    }
  }

  fun getColor(index: Int, highlight: Highlight = Highlight.Normal) = colors[index + (highlight.id shl 3)]

  enum class Highlight(val id: Int) {
    Normal(0), Light(1), Dark(2);

    companion object {
      fun byId(id: Int) = when (id) {
        Normal.id -> Normal
        Light.id -> Light
        Dark.id -> Dark
        else -> Normal
      }
    }
  }
}