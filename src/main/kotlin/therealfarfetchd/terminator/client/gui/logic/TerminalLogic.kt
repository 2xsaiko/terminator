package therealfarfetchd.terminator.client.gui.logic

import therealfarfetchd.quacklib.client.api.gui.AbstractGuiLogic
import therealfarfetchd.terminator.client.gui.element.Terminal
import therealfarfetchd.terminator.common.term.ITerminal

class TerminalLogic : AbstractGuiLogic() {
  val term: Terminal by component()

   val termImpl: ITerminal by params()

  override fun init() {}
}