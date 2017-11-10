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
import therealfarfetchd.terminator.client.font.FontTextureUtils
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.impl.NullTerminal

class Terminal : GuiElement() {
  var term_width: Int by number()
  var term_height: Int by number()

  init {
    term_width = 80
    term_height = 25
  }

  var terminal: ITerminal = NullTerminal

  override fun render(mouseX: Int, mouseY: Int) {
    drawBackground()
    if (terminal.cursor && System.currentTimeMillis() / 250 % 2 % 2 == 0L) {
      drawCursorAt(terminal.cursorX, terminal.cursorY)
    }
    val t = Tessellator.getInstance()
    val buf = t.buffer
    buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    for (x in 0 until terminal.width()) for (y in 0 until terminal.height()) {
      FontTextureUtils.draw(buf, terminal.get(x, y) ?: ' ', 2 + x * FontTextureUtils.charWidth, y * FontTextureUtils.charHeight, respectPosition = false)
    }
    t.draw()
  }

  fun drawBackground() {
    GlStateManager.disableTexture2D()
    val bgcol = 0xCF000000.toInt()
    val bordercol = 0xFF2160B1.toInt()
    Gui.drawRect(0, 0, width, height, bgcol)
    Gui.drawRect(0, 0, 2, height, bordercol)
    Gui.drawRect(width - 2, 0, width, height, bordercol)
    Gui.drawRect(2, height - 2, width - 2, height, bordercol)
    GlStateManager.enableTexture2D()
  }

  fun drawCursorAt(x: Int, y: Int) {
    GlStateManager.disableTexture2D()
    val x1 = x * FontTextureUtils.charWidth
    val y1 = y * FontTextureUtils.charHeight
    Gui.drawRect(2 + x1, y1 + FontTextureUtils.charHeight - 3, 2 + x1 + FontTextureUtils.charWidth, y1 + FontTextureUtils.charHeight - 1, 0xFFBBBBBB.toInt())
    GlStateManager.enableTexture2D()
  }

  override fun prepare(mouseX: Int, mouseY: Int) {
    terminal.resize(term_width, term_height)
    width = 4 + terminal.width() * FontTextureUtils.charWidth
    height = 2 + terminal.height() * FontTextureUtils.charHeight
  }
}