package therealfarfetchd.terminator.client.font

import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureUtil
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import therealfarfetchd.quacklib.client.RGBA
import therealfarfetchd.quacklib.common.api.util.plus
import therealfarfetchd.terminator.Terminator
import java.util.*

object FontTextureUtils {
  private val buffer = BufferUtils.createIntBuffer(65536)
  val fontTexture = TextureUtil.glGenTextures()
  var defaultGlyph: Char = '\u0000'; private set
  var glyphPosY: Map<Char, Int> = emptyMap(); private set
  var props: Map<Char, BDF.IGlyph> = emptyMap(); private set
  var width = 0; private set
  var height = 0; private set

  fun prepareFont(font: BDF) {
    val maxWidth = font.glyphs.values.maxBy { it.width }?.width?.powerOf2() ?: return
    val totalHeight = font.glyphs.values.sumBy { it.height }.powerOf2()
    glyphPosY = emptyMap()
    props = emptyMap()
    TextureUtil.allocateTexture(fontTexture, maxWidth, totalHeight)
    width = maxWidth
    height = totalHeight
    Terminator.Logger.info("Allocated ${maxWidth}x$totalHeight font texture")
    defaultGlyph = font.defaultGlyph
    var currentHeight = 0
    for ((k, v) in font.glyphs) {
      glyphPosY += k to currentHeight
      props += k to v
      setTexture(v.bits, currentHeight, v.width, v.height)
      currentHeight += v.height
    }
  }

  /**
   * Draw the specified char into the buffer at the specified position.
   * Returns the offset of the next glyph for convenience
   */
  fun draw(b: BufferBuilder, c: Char, x: Int, y: Int, color: RGBA = RGBA(1f, 1f, 1f, 1f)): Pair<Int, Int> {
    GlStateManager.bindTexture(fontTexture)
    GlStateManager.color(1f, 1f, 1f, 1f)
    val ty = glyphPosY[c] ?: glyphPosY[defaultGlyph] ?: return Pair(0, 0)
    val g = props[c] ?: props[defaultGlyph] ?: return Pair(0, 0)
    val tix = 0.0
    val tiy = ty / height.toDouble()
    val tiw = g.width / width.toDouble()
    val tih = g.height / height.toDouble()
    val px = x.toDouble() + g.xOff
    val py = y.toDouble() + g.yOff

    b.pos(px, py, 0.0).tex(tix, tiy).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px, py + g.height, 0.0).tex(tix, tiy + tih).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px + g.width, py + g.height, 0.0).tex(tix + tiw, tiy + tih).color(color.first, color.second, color.third, color.fourth).endVertex()
    b.pos(px + g.width, py, 0.0).tex(tix + tiw, tiy).color(color.first, color.second, color.third, color.fourth).endVertex()
    return Pair(g.dwidthX, g.dwidthY)
  }

  /**
   * Draws a string at the specified position. This is only used for testing, as the terminal uses a different implementation.
   */
  fun drawString(b: BufferBuilder, str: String, x: Int, y: Int, color: RGBA = RGBA(1f, 1f, 1f, 1f)) {
    var dw = Pair(x, y)
    for (c in str) {
      dw += draw(b, c, dw.first, dw.second, color)
    }
  }

  private fun setTexture(bitmap: BitSet, y: Int, width: Int, height: Int) {
    val data = IntArray(width * height) { if (bitmap[it]) -1 else 0 }
    buffer.put(data)
    buffer.flip()
    GlStateManager.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)
  }

  private fun Int.powerOf2(): Int {
    var n = this - 1
    n = n or n.ushr(1)
    n = n or n.ushr(2)
    n = n or n.ushr(4)
    n = n or n.ushr(8)
    n = n or n.ushr(16)
    return if (n < 0) 1 else if (n >= Integer.MAX_VALUE) Integer.MAX_VALUE else n + 1
  }
}