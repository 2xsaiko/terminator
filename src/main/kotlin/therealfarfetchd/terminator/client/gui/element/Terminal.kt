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

package therealfarfetchd.terminator.client.gui.element

import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import therealfarfetchd.quacklib.client.api.gui.GuiElement
import therealfarfetchd.quacklib.client.api.gui.number
import therealfarfetchd.quacklib.common.api.extensions.asColor
import therealfarfetchd.terminator.Terminator
import therealfarfetchd.terminator.client.Proxy
import therealfarfetchd.terminator.common.term.ColorPallette
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.impl.NullTerminal

class Terminal : GuiElement() {
  var term_width: Int by number()
  var term_height: Int by number()

  val pallette = ColorPallette()

  init {
    term_width = 80
    term_height = 25
  }

  var terminal: ITerminal = NullTerminal

  private val terminalFont
    get() = (Terminator.proxy as Proxy).terminalFont

  override fun render(mouseX: Int, mouseY: Int) {
    drawBackground()
    if (terminal.cursor() && System.currentTimeMillis() / 250 % 2 % 2 == 0L) {
      drawCursorAt(terminal.cursorX(), terminal.cursorY())
    }
    val t = Tessellator.getInstance()
    val buf = t.buffer
    buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    for (x in 0 until terminal.width()) for (y in 0 until terminal.height()) {
      terminalFont.draw(buf, terminal.get(x, y) ?: ' ', 2 + x * terminalFont.charWidth, y * terminalFont.charHeight, respectPosition = false,
        color = pallette.getColor(terminal.getFGCol(x, y) ?: 0, terminal.getHighlight(x, y) ?: ColorPallette.Highlight.Normal).asColor())
    }
    t.draw()
  }

  private fun drawBackground() {
    GlStateManager.disableTexture2D()
    for (x in 0 until terminal.width()) for (y in 0 until terminal.height()) {
      Gui.drawRect(2 + x * terminalFont.charWidth, y * terminalFont.charHeight,
        2 + (x + 1) * terminalFont.charWidth, (y + 1) * terminalFont.charHeight,
        0xCF000000.toInt() or pallette.getColor(terminal.getBGCol(x, y) ?: 0))
    }
    //    val bgcol = 0xCF000000.toInt()
    val bordercol = 0xFF2160B1.toInt()
    //    Gui.drawRect(0, 0, width, height, bgcol)
    Gui.drawRect(0, 0, 2, height, bordercol)
    Gui.drawRect(width - 2, 0, width, height, bordercol)
    Gui.drawRect(2, height - 2, width - 2, height, bordercol)
    GlStateManager.enableTexture2D()
  }

  private fun drawCursorAt(x: Int, y: Int) {
    if (x !in 0 until terminal.width() || y !in 0 until terminal.height()) return
    GlStateManager.disableTexture2D()
    val x1 = x * terminalFont.charWidth
    val y1 = y * terminalFont.charHeight
    Gui.drawRect(2 + x1, y1 + terminalFont.charHeight - 3, 2 + x1 + terminalFont.charWidth, y1 + terminalFont.charHeight - 1, 0xFFBBBBBB.toInt())
    GlStateManager.enableTexture2D()
  }

  override fun prepare(mouseX: Int, mouseY: Int) {
    terminal.resize(term_width, term_height)
    width = 4 + terminal.width() * terminalFont.charWidth
    height = 2 + terminal.height() * terminalFont.charHeight
  }
}