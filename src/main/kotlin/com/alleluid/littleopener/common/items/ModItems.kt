package com.alleluid.littleopener.common.items

import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
        @JvmStatic
    fun registerItems(registry: IForgeRegistry<Item>){
        // Register individuals
        registry.registerAll(
                CoordChecker
        )
    }

    @JvmStatic
    fun registerModels(){
        CoordChecker.registerItemModel()
    }

}