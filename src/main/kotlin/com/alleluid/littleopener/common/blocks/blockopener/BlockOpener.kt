package com.alleluid.littleopener.common.blocks.blockopener

import com.alleluid.littleopener.ConfigHandler
import com.alleluid.littleopener.CoordsMessage
import com.alleluid.littleopener.LittleOpenerMod
import com.alleluid.littleopener.PacketHandler
import com.alleluid.littleopener.client.GuiID
import com.alleluid.littleopener.common.blocks.BlockTileBase
import net.minecraft.block.Block
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

object BlockOpener : BlockTileBase<TileOpener>(Material.ROCK, "block_opener", GuiID.LITTLE_OPENER), ITileEntityProvider {
    init {
        blockHardness = 2f
    }

    override fun createNewTileEntity(worldIn: World, meta: Int) = TileOpener()
    override fun createTileEntity(world: World, state: IBlockState) = TileOpener()

    private var isOn: Boolean = false

    @SuppressWarnings("deprecation")
    override fun neighborChanged(state: IBlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos) {
        if (worldIn.getRedstonePowerFromNeighbors(pos) > 0)
        getTileEntity(worldIn, pos).onPowered()
    }

    override val tileEntityClass: Class<TileOpener>
        get() = TileOpener::class.java

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val tile = worldIn.getTileEntity(pos) as TileOpener
        return if (guiID != null) {
            when {
                playerIn.isSneaking -> {
                    if (!worldIn.isRemote) {
                        PacketHandler.INSTANCE.sendTo(CoordsMessage(pos, tile.targetPos), playerIn as EntityPlayerMP)
                    }
                    playerIn.openGui(LittleOpenerMod.instance, guiID.ordinal, worldIn, pos.x, pos.y, pos.z)
                    true
                }
                ConfigHandler.isButton == true -> {
                    tile.onPowered()
                    true
                }
                else -> false
            }
        } else
            super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
    }

    override fun getWeakChanges(world: IBlockAccess, pos: BlockPos) = true
}