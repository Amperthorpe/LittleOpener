package com.alleluid.littleopener.common.items

import com.alleluid.littleopener.ConfigHandler
import com.alleluid.littleopener.LittleOpenerMod
import com.alleluid.littleopener.MOD_ID
import com.alleluid.littleopener.common.blocks.blockopener.BlockOpener
import com.alleluid.littleopener.common.blocks.blockopener.TileOpener
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World

object CoordChecker : Item() {
    private const val name = "coord_checker"

    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = CreativeTabs.REDSTONE
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add(I18n.format("text.littleopener.coord_checker.info1"))
        tooltip.add(I18n.format("text.littleopener.coord_checker.info2"))
        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = player.getHeldItem(hand)
        if (!stack.hasTagCompound()) {
            stack.tagCompound = NBTTagCompound()
        }

        if (worldIn.getBlockState(pos).block is BlockOpener) {
            val tileOpener = worldIn.getTileEntity(pos) as TileOpener
            val newPos = BlockPos.fromLong(stack.tagCompound!!.getLong("targetPos"))
            if (tileOpener.checkRange(newPos)){
                tileOpener.targetPos = newPos
                if (worldIn.isRemote) player.sendStatusMessage(TextComponentTranslation("text.littleopener.coord_checker.new_pos", newPos), true)
            } else {
                if (worldIn.isRemote) player.sendStatusMessage(TextComponentTranslation("text.littleopener.coord_checker.too_far", ConfigHandler.maxDistance), true)
            }
        } else {
            stack.tagCompound!!.setLong("targetPos", pos.toLong())
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ)
    }

    fun registerItemModel() {
        LittleOpenerMod.proxy.registerItemRenderer(this, 0, name)
    }
}