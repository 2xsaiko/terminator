package therealfarfetchd.terminator.common.interpreter.forth

import therealfarfetchd.kforth.light.Forth
import therealfarfetchd.kforth.light.Terminal
import therealfarfetchd.terminator.common.interpreter.Environment
import therealfarfetchd.terminator.common.interpreter.IInterpreter
import therealfarfetchd.terminator.common.term.ITerminal

class ForthInterpreter : IInterpreter {
  override fun start(env: Environment) {
    val forth = Forth()
    forth.term = ForthTerm(env.term)
    forth.mainLoop()
  }
}

class ForthTerm(val tw: ITerminal) : Terminal {
  override fun clear() {
    tw.clear()
    tw.cursorX(0)
    tw.cursorY(0)
  }

  override fun width(): Int {
    return tw.width()
  }

  override fun height(): Int {
    return tw.height()
  }

  override fun read(): Char? {
    return tw.read()?.first
  }

  override fun readBlocking(): Char {
    while (true) {
      val x = read()
      if (x != null) return x
      Thread.sleep(100L)
    }
  }

  override fun readLine(len: Int): String {
    var buffer = ""
    while (true) {
      val c = readBlocking()
      println(c.toInt())
      when (c) {
        '\r' -> {
          write(' ')
          return buffer
        }
        '\b' -> {
          if (tw.cursorX() != 0 && buffer.isNotEmpty()) {
            buffer = buffer.dropLast(1)
            tw.cursorX(tw.cursorX() - 1)
            write(' ')
            tw.cursorX(tw.cursorX() - 1)
          }
        }
        else -> {
          if (buffer.length < len && c.isDefined()) {
            write(c)
            buffer += c
          }
        }
      }
    }
  }

  override fun write(c: Char) {
    when (c) {
      '\n' -> {
        tw.cursorX(0)
        if (tw.cursorY() == height() - 1) {
          tw.scroll()
        } else {
          tw.cursorY(tw.cursorY() + 1)
        }
      }
      else -> {
        tw.put(tw.cursorX(), tw.cursorY(), c)
        if (tw.cursorX() == width() - 1) {
          write('\n')
        } else {
          tw.cursorX(tw.cursorX() + 1)
        }
      }
    }
  }
}