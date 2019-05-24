package com.alleluid.littleopener

import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ChatType
import net.minecraft.util.text.TextComponentString

object Utils {
    const val validIntChars = "1234567890-"

    val keyForward get() = Minecraft.getMinecraft().gameSettings.keyBindForward
    val keyBack get() = Minecraft.getMinecraft().gameSettings.keyBindBack
    val keyInv get() = Minecraft.getMinecraft().gameSettings.keyBindInventory
    val keyRight get() = Minecraft.getMinecraft().gameSettings.keyBindRight

    val specialCharacterCodes = listOf(
            14,         //Backspace
            221,        //Delete
            203,        //Left Arrow
            205        //Right Arrow
    )

    @JvmStatic fun BlockPos.intMin() = BlockPos(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    @JvmStatic fun intMinBlockPos() = BlockPos(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

    fun statusMessage(msg: Any) = Minecraft.getMinecraft().ingameGUI.addChatMessage(ChatType.GAME_INFO, TextComponentString(msg.toString()))
    fun chatMessage(msg: Any) = Minecraft.getMinecraft().ingameGUI.addChatMessage(ChatType.SYSTEM, TextComponentString(msg.toString()))

}