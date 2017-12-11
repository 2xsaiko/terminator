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

package therealfarfetchd.terminator.client

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import therealfarfetchd.quacklib.client.api.gui.GuiApi
import therealfarfetchd.quacklib.client.api.gui.GuiElementRegistry
import therealfarfetchd.quacklib.client.api.gui.GuiLogicRegistry
import therealfarfetchd.terminator.ModID
import therealfarfetchd.terminator.client.font.BDFParser
import therealfarfetchd.terminator.client.font.FontTextureUtils
import therealfarfetchd.terminator.client.gui.element.Terminal
import therealfarfetchd.terminator.client.gui.logic.TerminalLogic
import therealfarfetchd.terminator.client.settings.Keybindings
import therealfarfetchd.terminator.common.Proxy
import therealfarfetchd.terminator.common.interpreter.InterpreterManager
import therealfarfetchd.terminator.common.term.impl.StandardTerminal

class Proxy : Proxy() {
  val mc = Minecraft.getMinecraft()

  val terminalGui = ResourceLocation(ModID, "terminal")

  private var _terminalFont: FontTextureUtils? = null
  val terminalFont: FontTextureUtils
    get() =
      if (_terminalFont == null)
        FontTextureUtils(BDFParser.read(ResourceLocation(ModID, "fonts/terminus/ter-u16n.bdf")))
          .also { _terminalFont = it }
      else _terminalFont!!

  override fun preInit(e: FMLPreInitializationEvent) {
    super.preInit(e)

    GuiElementRegistry.register("$ModID:terminal", ::Terminal)
    GuiLogicRegistry.register(terminalGui, ::TerminalLogic)
  }

  override fun postInit(e: FMLPostInitializationEvent) {
    super.postInit(e)
    InterpreterManager.setOutputTerminal(StandardTerminal)
    InterpreterManager.start()
  }

  @SubscribeEvent
  fun handleKeys(e: InputEvent.KeyInputEvent) {
    while (Keybindings.OpenTerminal.isPressed) {
      if (mc.currentScreen == null) {
        mc.displayGuiScreen(GuiApi.loadGui(terminalGui, mapOf("termImpl" to StandardTerminal)))
      }
    }
  }

  @SubscribeEvent
  fun onReloadTextures(e: TextureStitchEvent.Pre) {
    _terminalFont?.destroy()
    _terminalFont = null
    terminalFont
  }
}