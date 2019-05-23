package com.alleluid.littleopener.proxy

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import com.alleluid.littleopener.MOD_ID

open class CommonProxy{
    open fun registerItemRenderer(item: Item, meta: Int, id: String){}
}

class ClientProxy: CommonProxy(){
        override fun registerItemRenderer(item: Item, meta: Int, id: String) {
        ModelLoader.setCustomModelResourceLocation(
                item, meta, ModelResourceLocation("$MOD_ID:$id", "inventory")
        )
    }

}