package com.alleluid.littleopener

import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos

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

val errorBlockPos get() = BlockPos(-1, -1, -1)
