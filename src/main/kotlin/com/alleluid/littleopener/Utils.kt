package com.alleluid.littleopener

import net.minecraft.client.Minecraft

object Utils {
    const val validIntChars = "1234567890-"

    const val CODE_TAB = 15
    val keyForward = Minecraft.getMinecraft().gameSettings.keyBindForward
    val keyBack = Minecraft.getMinecraft().gameSettings.keyBindBack
    val keyInv = Minecraft.getMinecraft().gameSettings.keyBindInventory

    val specialCharacterCodes = listOf(
            14,         //Backspace
            221,        //Delete
            203,        //Left Arrow
            205,        //Right Arrow
            15          //Tab
            )
}