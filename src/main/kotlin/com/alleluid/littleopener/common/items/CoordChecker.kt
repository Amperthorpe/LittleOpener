package com.alleluid.littleopener.common.items

import com.alleluid.littleopener.LittleOpenerMod
import com.alleluid.littleopener.MOD_ID
import net.minecraft.client.Minecraft
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ChatType
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

object CoordChecker : Item(){
    private const val name = "coord_checker"
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = CreativeTabs.REDSTONE
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (worldIn.isRemote){
            Minecraft.getMinecraft().ingameGUI.addChatMessage(ChatType.SYSTEM, TextComponentString(pos.toString()))
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

    fun registerItemModel() {
    LittleOpenerMod.proxy.registerItemRenderer(this, 0, name)
    }
}