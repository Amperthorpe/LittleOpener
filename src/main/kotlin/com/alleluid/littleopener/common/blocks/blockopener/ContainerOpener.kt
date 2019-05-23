package com.alleluid.littleopener.common.blocks.blockopener

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ContainerOpener(val world: World, posTE: BlockPos) : Container(){
    val tileOpener: TileOpener = (world.getTileEntity(posTE) as TileOpener)
    var targetPos: BlockPos = BlockPos(-1000, 100, -1000) //TODO make this origin

    override fun canInteractWith(playerIn: EntityPlayer) = !playerIn.isSneaking

    override fun onContainerClosed(playerIn: EntityPlayer) {
        tileOpener.targetPos = targetPos
        this.detectAndSendChanges()
        super.onContainerClosed(playerIn)
    }

}