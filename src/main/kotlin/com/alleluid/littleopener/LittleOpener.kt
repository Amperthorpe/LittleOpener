package com.alleluid.littleopener

import com.alleluid.littleopener.client.GuiHandler
import com.alleluid.littleopener.common.blocks.ModBlocks
import com.alleluid.littleopener.common.items.ModItems
import com.alleluid.littleopener.proxy.CommonProxy
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry


const val MOD_ID = "littleopener"
const val MOD_NAME = "Little Opener"
const val VERSION = "1.0.1"


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
        ConfigHandler.init(event.suggestedConfigurationFile)
    }

    @Mod.EventHandler
    fun initEvent(event: FMLInitializationEvent){
        ModBlocks.registerTileEntities()
        PacketHandler.registerMessages()
    }

    @SubscribeEvent
    fun onConfigChangedEvent(event: ConfigChangedEvent.OnConfigChangedEvent){
    }

    @SidedProxy(serverSide = "com.alleluid.littleopener.proxy.CommonProxy",
            clientSide = "com.alleluid.littleopener.proxy.ClientProxy")
    var proxy = CommonProxy()

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