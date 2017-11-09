package therealfarfetchd.terminator.client.settings

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.input.Keyboard
import therealfarfetchd.quacklib.common.api.util.AutoLoad
import therealfarfetchd.terminator.ModID

@AutoLoad
@SideOnly(Side.CLIENT)
object Keybindings {
  val OpenTerminal = KeyBinding("$ModID:open_terminal", Keyboard.KEY_F12, "key.categories.multiplayer")

  init {
    ClientRegistry.registerKeyBinding(OpenTerminal)
  }
}