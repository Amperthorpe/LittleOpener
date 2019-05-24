package com.alleluid.littleopener

import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation

val keyForward: KeyBinding get() = Minecraft.getMinecraft().gameSettings.keyBindForward
val keyBack: KeyBinding get() = Minecraft.getMinecraft().gameSettings.keyBindBack
val keyInv: KeyBinding get() = Minecraft.getMinecraft().gameSettings.keyBindInventory

const val validIntChars = "1234567890-"

val specialCharacterCodes = listOf(
        14,         //Backspace
        221,        //Delete
        203,        //Left Arrow
        205        //Right Arrow
)

val errorBlockPos get() = BlockPos(-1, -1, -1)

fun playerMessage(player: EntityPlayer, keyName: String, vararg args: Any?){
    if (player.world.isRemote) player.sendStatusMessage(TextComponentTranslation("text.littleopener.$keyName", args), true)
}