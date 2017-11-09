package therealfarfetchd.terminator.common.term.impl

import therealfarfetchd.terminator.common.term.ITerminal

object StandardTerminal : ITerminal {
  override fun read(): Char? {
    TODO("not implemented")
  }

  override fun resetInput() {
    TODO("not implemented")
  }

  override fun put(x: Int, y: Int, ch: Char) {
    TODO("not implemented")
  }

  override fun width(): Int {
    TODO("not implemented")
  }

  override fun height(): Int {
    TODO("not implemented")
  }

  override var cursor: Boolean = true

  override fun scroll(n: Int) {
    TODO("not implemented")
  }

  override fun scrollUp(n: Int) {
    TODO("not implemented")
  }

  override fun scrollLeft(n: Int) {
    TODO("not implemented")
  }

  override fun scrollRight(n: Int) {
    TODO("not implemented")
  }
}