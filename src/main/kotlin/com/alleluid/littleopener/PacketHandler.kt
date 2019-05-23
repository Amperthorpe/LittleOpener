package com.alleluid.littleopener

import com.alleluid.littleopener.client.GuiOpener
import com.alleluid.littleopener.common.blocks.blockopener.TileOpener
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side
import scala.collection.parallel.ParIterableLike

object PacketHandler {
    @JvmStatic
    val INSTANCE = SimpleNetworkWrapper(MOD_ID)
    @JvmStatic
    var uid = 0

    @JvmStatic
    fun registerMessages() {
        INSTANCE.registerMessage(CoordsMessage.SaveCoordsMessageHandler::class.java, CoordsMessage::class.java, uid++, Side.SERVER)
        INSTANCE.registerMessage(CoordsMessage.LoadCoordsMessageHandler::class.java, CoordsMessage::class.java, uid++, Side.CLIENT)
    }
}

class CoordsMessage(var toSendXTE: Int, var toSendYTE: Int, var toSendZTE: Int,
                    var toSendXLT: Int, var toSendYLT: Int, var toSendZLT: Int) : IMessage {

    constructor(blockPosTE: BlockPos, blockPosLT: BlockPos) :
            this(blockPosTE.x, blockPosTE.y, blockPosTE.z,
                    blockPosLT.x, blockPosLT.y, blockPosLT.z)

    constructor() : this(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

    override fun toBytes(buf: ByteBuf?) {
        buf?.writeInt(toSendXTE)
        buf?.writeInt(toSendYTE)
        buf?.writeInt(toSendZTE)

        buf?.writeInt(toSendXLT)
        buf?.writeInt(toSendYLT)
        buf?.writeInt(toSendZLT)
    }

    override fun fromBytes(buf: ByteBuf?) {
        toSendXTE = buf?.readInt() ?: Int.MIN_VALUE
        toSendYTE = buf?.readInt() ?: Int.MIN_VALUE
        toSendZTE = buf?.readInt() ?: Int.MIN_VALUE

        toSendXLT = buf?.readInt() ?: Int.MIN_VALUE
        toSendYLT = buf?.readInt() ?: Int.MIN_VALUE
        toSendZLT = buf?.readInt() ?: Int.MIN_VALUE
    }

    class SaveCoordsMessageHandler : IMessageHandler<CoordsMessage, IMessage> {
        override fun onMessage(message: CoordsMessage?, ctx: MessageContext?): IMessage? {
            if (message != null && ctx != null) {
                //Check for values as a result of nulls
                if (listOf(message.toSendXTE, message.toSendYTE, message.toSendZTE,
                                message.toSendXLT, message.toSendYLT, message.toSendZLT)
                                .contains(Int.MIN_VALUE))
                    return null
                val posTE = BlockPos(message.toSendXTE, message.toSendYTE, message.toSendZTE)
                val posLT = BlockPos(message.toSendXLT, message.toSendYLT, message.toSendZLT)
                println("posTE:$posTE | posLT:$posLT")
                val serverWorld = ctx.serverHandler.player.serverWorld
                serverWorld.addScheduledTask {
                    val opener = serverWorld.getTileEntity(posTE) as TileOpener
                    opener.targetPos = posLT
                }
            }
            return null
        }
    }

    class LoadCoordsMessageHandler : IMessageHandler<CoordsMessage, IMessage> {
        override fun onMessage(message: CoordsMessage?, ctx: MessageContext?): IMessage? {
            if (message != null) {
                val posTE = BlockPos(message.toSendXTE, message.toSendYTE, message.toSendZTE)
                val posLT = BlockPos(message.toSendXLT, message.toSendYLT, message.toSendZLT)
                Minecraft.getMinecraft().addScheduledTask {
                    val opener = Minecraft.getMinecraft().world.getTileEntity(posTE) as TileOpener
                    opener.targetPos = posLT
                    val screen = Minecraft.getMinecraft().currentScreen
                    if (screen is GuiOpener){
                        screen.setFields(posLT)
                    }
                }
            }
            return null
        }
    }

}
