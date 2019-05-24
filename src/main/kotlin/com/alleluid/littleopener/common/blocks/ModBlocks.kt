package com.alleluid.littleopener.common.blocks

import com.alleluid.littleopener.LittleOpenerMod
import com.alleluid.littleopener.MOD_ID
import com.alleluid.littleopener.client.GuiID
import com.alleluid.littleopener.common.blocks.blockopener.BlockOpener
import com.alleluid.littleopener.common.blocks.blockopener.TileOpener
import net.minecraft.block.Block
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistry
import java.util.*

object ModBlocks {
    @JvmStatic
    fun register(registry: IForgeRegistry<Block>) {
        registry.register(
                BlockOpener
        )

    }

    @JvmStatic
    fun registerItemBlocks(registry: IForgeRegistry<Item>) {
        registry.register(
                BlockOpener.createItemBlock()
        )
    }

    @JvmStatic
    fun registerModels() {
        BlockOpener.registerItemModel()
    }

    @JvmStatic
    fun registerTileEntities() {
        TileOpener().registerTE()
    }

}

abstract class BlockTileBase<TE : TileEntity>(material: Material, private val name: String, val guiID: GuiID?) : Block(material, MapColor.BLACK) {
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = LittleOpenerMod.creativeTab
    }
    abstract val tileEntityClass: Class<TE>
    abstract override fun createTileEntity(world: World, state: IBlockState): TE?
    override fun hasTileEntity(state: IBlockState) = true
    fun getTileEntity(world: IBlockAccess, pos: BlockPos): TE = world.getTileEntity(pos) as TE

        override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return Item.getItemFromBlock(this)
    }

    open fun registerItemModel() {
        LittleOpenerMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, name)
    }

    open fun createItemBlock(): Item {
        return ItemBlock(this).setRegistryName(registryName)
    }

}