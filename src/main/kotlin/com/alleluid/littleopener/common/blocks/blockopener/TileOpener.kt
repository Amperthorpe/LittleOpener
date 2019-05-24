package com.alleluid.littleopener.common.blocks.blockopener

import com.alleluid.littleopener.ConfigHandler
import com.alleluid.littleopener.MOD_ID
import io.netty.buffer.ByteBuf
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.CommandBlockBaseLogic
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

class TileOpener : TileEntity() {
    var isTriggered = false
    private val name = "tile_opener"
    private var _targetPos: BlockPos = pos
    var targetPos: BlockPos // Will be 0,0,0 on init
        get() = _targetPos
        set(value) {
            if (checkRange(value)){
                _targetPos = value
                markDirty()
            }
        }

    fun checkRange(checkPos: BlockPos = targetPos): Boolean {
        val maxDist = ConfigHandler.maxDistance?.toDouble() ?: return false
        if (maxDist < 1) return true
        return pos.getDistance(checkPos.x, checkPos.y, checkPos.z) <= maxDist
    }

    fun activate() {
        if (!world.isRemote && world.isBlockLoaded(targetPos)) {
            if (!checkRange()) {
                targetPos = BlockPos.ORIGIN
                return
            }
            val cmdSender = CommandLogic(world)
            val rawCommand = "/lt-open ${targetPos.x} ${targetPos.y} ${targetPos.z}"
            val serv = world.minecraftServer
            serv?.commandManager?.executeCommand(cmdSender, rawCommand)
        }
    }

    fun redstoneHandling(isPowered: Boolean){
        if (isPowered && !isTriggered){
            isTriggered = true
            activate()
        } else if (!isPowered && isTriggered){
            isTriggered = false
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
            _targetPos = BlockPos(x, y, z)
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