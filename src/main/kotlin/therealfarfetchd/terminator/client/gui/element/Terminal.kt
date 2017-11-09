package therealfarfetchd.terminator.client.gui.element

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import therealfarfetchd.quacklib.client.RGBA
import therealfarfetchd.quacklib.client.api.gui.GuiElement
import therealfarfetchd.terminator.client.font.FontTextureUtils

class Terminal : GuiElement() {
  val cwidth = 80
  val cheight = 25

  override fun render(mouseX: Int, mouseY: Int) {
//    GlStateManager.disableTexture2D()
//    GlStateManager.disableAlpha()
    val t = Tessellator.getInstance()
    val buf = t.buffer
    buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
    FontTextureUtils.drawString(buf, "Hello world! ABCDEFGHIJKLMNOPQRSTUVWXYZ@", -50, 10, RGBA(0.2f, 0.2f, 0.7f, 1.0f))
    t.draw()
  }
}