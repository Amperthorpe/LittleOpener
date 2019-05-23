package com.alleluid.littleopener

import com.alleluid.littleopener.client.GuiHandler
import com.alleluid.littleopener.common.blocks.ModBlocks
import com.alleluid.littleopener.common.items.ModItems
import com.alleluid.littleopener.proxy.CommonProxy
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.world.World
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.world.WorldServer
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import org.apache.logging.log4j.ThreadContext.containsKey



const val MOD_ID = "littleopener"
const val MOD_NAME = "Little Opener"
const val VERSION = "1.0-SNAPSHOT"


@Mod(
        modid = MOD_ID,
        name = MOD_NAME,
        version = VERSION,
        modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
        useMetadata = true,
        dependencies = "required-after:forgelin;"
)

object LittleOpenerMod{

    @Mod.EventHandler
    fun preInitEvent(event: FMLPreInitializationEvent){
        NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandler())
    }

    @Mod.EventHandler
    fun initEvent(event: FMLInitializationEvent){
        ModBlocks.registerTileEntities()
        PacketHandler.registerMessages()
    }

    @SidedProxy(serverSide = "com.alleluid.littleopener.proxy.CommonProxy",
            clientSide = "com.alleluid.littleopener.proxy.ClientProxy")
    var proxy = CommonProxy()

    @Mod.Instance
    var instance = LittleOpenerMod

    @JvmStatic
    val creativeTab = CreativeTabs.REDSTONE
}

@Mod.EventBusSubscriber(modid = MOD_ID)
object RegistryHandler {
    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        ModItems.registerItems(event.registry)
        ModBlocks.registerItemBlocks(event.registry)
    }

    @JvmStatic
    @SubscribeEvent
    fun onModelRegister(event: ModelRegistryEvent) {
        ModItems.registerModels()
        ModBlocks.registerModels()
    }

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        ModBlocks.register(event.registry)
    }
}