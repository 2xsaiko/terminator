package therealfarfetchd.terminator

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager
import therealfarfetchd.terminator.common.Proxy

const val ModID = "terminator"
const val ClientProxy = "therealfarfetchd.$ModID.client.Proxy"
const val ServerProxy = "therealfarfetchd.$ModID.common.Proxy"

@Mod(modid = ModID, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object Terminator {
  val Logger = LogManager.getLogger(ModID)!!

  @SidedProxy(clientSide = ClientProxy, serverSide = ServerProxy)
  lateinit var proxy: Proxy

  @Mod.EventHandler
  fun preInit(e: FMLPreInitializationEvent) = proxy.preInit(e)

  @Mod.EventHandler
  fun init(e: FMLInitializationEvent) = proxy.init(e)

  @Mod.EventHandler
  fun postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)
}