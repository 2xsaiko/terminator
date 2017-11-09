package therealfarfetchd.terminator.client

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.TextureStitchEvent
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
import therealfarfetchd.terminator.common.term.impl.StandardTerminal

class Proxy : Proxy() {
  val mc = Minecraft.getMinecraft()

  val terminalGui = ResourceLocation(ModID, "terminal")

  override fun preInit(e: FMLPreInitializationEvent) {
    super.preInit(e)

    GuiElementRegistry.register("$ModID:terminal", ::Terminal)
    GuiLogicRegistry.register(terminalGui, ::TerminalLogic)
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
    FontTextureUtils.prepareFont(BDFParser.read(ResourceLocation(ModID, "fonts/terminus/ter-u24b.bdf")))
  }
}