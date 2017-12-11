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

package therealfarfetchd.terminator.client.font

import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureUtil
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import therealfarfetchd.quacklib.common.api.extensions.RGBA
import therealfarfetchd.quacklib.common.api.extensions.ielse
import therealfarfetchd.quacklib.common.api.extensions.iif
import therealfarfetchd.quacklib.common.api.util.plus
import therealfarfetchd.terminator.Terminator
import java.util.*

class FontTextureUtils(val font: BDF) {
  private val buffer = BufferUtils.createIntBuffer(65536)

  var textures: List<Int> = emptyList(); private set

  var glyphPosY: Map<Char, Int> = emptyMap(); private set
  var textureIndex: Map<Char, Int> = emptyMap(); private set

  var widthMap: Map<Int, Int> = emptyMap(); private set
  var heightMap: Map<Int, Int> = emptyMap(); private set

  var charWidth = 0; private set
  var charHeight = 0; private set

  private val maxTexSize = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE)

  private var destroyed = false

  init {
    Terminator.Logger.info("Max texture size: $maxTexSize")
    allocateTextures()

    var currentHeight = 0
    var currentTex = 0
    for ((k, v) in font.glyphs) {
      charWidth = maxOf(charWidth, v.width)
      charHeight = maxOf(charHeight, v.height)

      if (currentHeight + v.height > heightMap[currentTex]!!) {
        currentHeight = 0
        currentTex++
        if (currentTex > textures.size)
          error("A fatal error occurred while writing texture sheet. This shouldn't ever happen unless this code has a bug. RIP")
      }

      glyphPosY += k to currentHeight
      textureIndex += k to currentTex

      setTexture(v.bits, currentTex, currentHeight, v.width, v.height)
      currentHeight += v.height
    }
  }

  /**
   * Draw the specified char into the buffer at the specified position.
   * Returns the offset of the next glyph for convenience
   */
  fun draw(b: BufferBuilder, c: Char, x: Int, y: Int, color: RGBA = RGBA(0.75f, 0.75f, 0.75f, 1f), respectPosition: Boolean = true): Pair<Int, Int> {
    val texIndex = textureIndex[c] ?: return Pair(0, 0)
    GlStateManager.bindTexture(textures[texIndex])
    val height = heightMap[texIndex]!!
    val width = widthMap[texIndex]!!
    GlStateManager.color(1f, 1f, 1f, 1f)
    val ty = glyphPosY[c] ?: glyphPosY[font.defaultGlyph] ?: return Pair(0, 0)
    val g = font.glyphs[c] ?: font.glyphs[font.defaultGlyph] ?: return Pair(0, 0)
    val tix = 0.0
    val tiy = ty / height.toDouble()
    val tiw = g.width / width.toDouble()
    val tih = g.height / height.toDouble()
    val px = x.toDouble() + if (respectPosition) g.xOff else 0
    val py = y.toDouble() + if (respectPosition) g.yOff else 0

    b.pos(px, py, 0.0).tex(tix, tiy).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px, py + g.height, 0.0).tex(tix, tiy + tih).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px + g.width, py + g.height, 0.0).tex(tix + tiw, tiy + tih).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px + g.width, py, 0.0).tex(tix + tiw, tiy).color(color.first, color.second, color.third, color.fourth).endVertex()
    return Pair(g.dwidthX, g.dwidthY)
  }

  /**
   * Draws a string at the specified position, respecting device width.
   * This is unused, as the terminal uses a different implementation.
   */
  fun drawString(b: BufferBuilder, str: String, x: Int, y: Int, color: RGBA = RGBA(0.75f, 0.75f, 0.75f, 1f)) {
    var dw = Pair(x, y)
    for (c in str) {
      dw += draw(b, c, dw.first, dw.second, color)
    }
  }

  private fun setTexture(bitmap: BitSet, texture: Int, y: Int, width: Int, height: Int) {
    val data = IntArray(width * height) { if (bitmap[it]) -1 else 0 }
    buffer.put(data)
    buffer.flip()
    GlStateManager.bindTexture(textures[texture])
    GlStateManager.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)
  }

  private fun allocateTextures() {
    val textureWidth = (font.glyphs.values.maxBy { it.width }?.width ?: 0).powerOf2()

    var heightTmp = font.glyphs.values.sumBy { it.height }
    var texcount = 1
    while (heightTmp > maxTexSize) {
      heightTmp -= maxTexSize
      texcount++
    }
    for (i in 0 until texcount) {
      val height = (i < texcount - 1)
        .iif { maxTexSize } // Full-size textures
        .ielse { heightTmp.powerOf2() }

      val texid = TextureUtil.glGenTextures()
      textures += texid
      widthMap += i to textureWidth
      heightMap += i to height
      TextureUtil.allocateTexture(texid, textureWidth, height)
      Terminator.Logger.info("Allocated $textureWidth*$height texture")
    }
  }

  // No idea how this works, found on StackOverflow :P
  private fun Int.powerOf2(): Int {
    var n = this - 1
    n = n or n.ushr(1)
    n = n or n.ushr(2)
    n = n or n.ushr(4)
    n = n or n.ushr(8)
    n = n or n.ushr(16)
    return if (n < 0) 1 else if (n >= Integer.MAX_VALUE) Integer.MAX_VALUE else n + 1
  }

  fun destroy() {
    textures.forEach(TextureUtil::deleteTexture)
    destroyed = true
  }
}