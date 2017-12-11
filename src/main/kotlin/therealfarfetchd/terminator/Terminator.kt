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

package therealfarfetchd.terminator

import net.minecraftforge.fml.common.Loader
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

  val version by lazy { Loader.instance().reversedModObjectList[this]?.version ?: "custom" }

  @SidedProxy(clientSide = ClientProxy, serverSide = ServerProxy)
  lateinit var proxy: Proxy

  @Mod.EventHandler
  fun preInit(e: FMLPreInitializationEvent) = proxy.preInit(e)

  @Mod.EventHandler
  fun init(e: FMLInitializationEvent) = proxy.init(e)

  @Mod.EventHandler
  fun postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)
}