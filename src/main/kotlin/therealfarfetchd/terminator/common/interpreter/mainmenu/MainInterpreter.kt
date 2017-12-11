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

package therealfarfetchd.terminator.common.interpreter.mainmenu

import therealfarfetchd.terminator.Terminator
import therealfarfetchd.terminator.common.interpreter.Environment
import therealfarfetchd.terminator.common.interpreter.IInterpreter
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.moveCursor
import therealfarfetchd.terminator.common.term.newLine

class MainInterpreter : IInterpreter {
  override fun start(env: Environment) {
    val t = env.term

    t.resetAttrib()
    t.resetInput()
    t.clear()

    t.cursorX(0)
    t.cursorY(0)
    t.cursor(true)

    t.println("Version ${Terminator.version}")

    for (bg in 0..7) {
      t.println("")
      t.setBGCol(bg)
      for (fg in 0..7) {
        t.setFGCol(fg)
        t.print(" F:$fg B:$bg ")
      }
    }

    while (true) Thread.sleep(1000)
  }

  fun ITerminal.print(s: String) = s.forEach { print(it) }

  fun ITerminal.print(c: Char) = when (c) {
    '\n' -> newLine()
    else -> {
      put(cursorX(), cursorY(), c)
      moveCursor()
    }
  }

  fun ITerminal.println(s: String) = print("$s\n")
}