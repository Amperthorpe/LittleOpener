package com.alleluid.littleopener.common.blocks.blockopener

import com.alleluid.littleopener.MOD_ID
import com.mojang.authlib.GameProfile
import io.netty.buffer.ByteBuf
import net.minecraft.block.state.IBlockState
import net.minecraft.command.CommandSenderWrapper
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.CommandBlockBaseLogic
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.FakePlayer
import net.minecraftforge.common.util.FakePlayerFactory
import net.minecraftforge.fml.common.registry.GameRegistry
import java.util.*

class TileOpener : TileEntity() {
    private val name = "tile_opener"
    var targetPos: BlockPos = pos // Will be 0,0,0 on init
        set(value) {
            println("setTargetPos:$value")
            field = value
            markDirty()
        }

    fun onPowered() {
        println("onPowered:$targetPos")
        //TODO: ensure block is loaded first
        if (!world.isRemote) {
            val cmdSender = CommandLogic(world)
            val rawCommand = "/lt-open ${targetPos.x} ${targetPos.y} ${targetPos.z}"
            println("Cmd: $rawCommand")
            val serv = world.minecraftServer
            if (serv != null) {
                println(serv.canUseCommand(2, "time"))
                serv.commandManager.executeCommand(cmdSender, rawCommand)
            } else
                println("It was null")
        }
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("targetPosX", targetPos.x)
        compound.setInteger("targetPosY", targetPos.y)
        compound.setInteger("targetPosZ", targetPos.z)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        if (compound.hasKey("targetPosX")) {
            val x = compound.getInteger("targetPosX")
            val y = compound.getInteger("targetPosY")
            val z = compound.getInteger("targetPosZ")
            targetPos = BlockPos(x, y, z)
        }
        super.readFromNBT(compound)
    }

    fun registerTE() {
        GameRegistry.registerTileEntity(this::class.java, ResourceLocation(MOD_ID, this.name))
    }
}

class CommandLogic(val world: World) : CommandBlockBaseLogic(){
    init {
        name = "[LittleOpener]"
    }
    override fun updateCommand() {
    }

    override fun getCommandBlockType() = 0

    override fun fillInInfo(buf: ByteBuf) {
    }

    override fun getEntityWorld() = world

    override fun getServer(): MinecraftServer? {
        return world.minecraftServer
    }

}