package com.alleluid.littleopener.client

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

enum class GuiID { LITTLE_OPENER }

class GuiHandler : IGuiHandler{
    override fun getClientGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
        return when (GuiID.values()[ID]) {
            GuiID.LITTLE_OPENER -> GuiOpener(BlockPos(x, y, z))
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
        return when (GuiID.values()[ID]) {
            GuiID.LITTLE_OPENER -> null
        }
    }
}