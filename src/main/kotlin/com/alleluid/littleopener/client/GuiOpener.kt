package com.alleluid.littleopener.client

import com.alleluid.littleopener.PacketHandler
import com.alleluid.littleopener.CoordsMessage
import com.alleluid.littleopener.Utils
import com.alleluid.littleopener.common.blocks.blockopener.TileOpener
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import net.minecraft.util.math.BlockPos

class GuiOpener(val openerPos: BlockPos) : GuiScreen() {
    val X_FIELD = 0
    val Y_FIELD = 1
    val Z_FIELD = 2
    lateinit var xField: GuiTextField
    lateinit var yField: GuiTextField
    lateinit var zField: GuiTextField
    private lateinit var fields: List<GuiTextField>

    override fun initGui() {
        super.initGui()
        //Centers, adjusts for offset, adjusts for coords referring to left side
        val targetPos = (Minecraft.getMinecraft().world.getTileEntity(openerPos) as TileOpener).targetPos
        xField = GuiTextField(X_FIELD, fontRenderer, width / 2 - 50 - 20, height / 2, 40, 15)
        yField = GuiTextField(Y_FIELD, fontRenderer, width / 2 - 0 - 20, height / 2, 40, 15)
        zField = GuiTextField(Z_FIELD, fontRenderer, width / 2 + 50 - 20, height / 2, 40, 15)
        fields = listOf(xField, yField, zField)
        fields.forEach {
            it.maxStringLength = 10
            it.isFocused = false
            it.visible = true
        }

        xField.apply { text = targetPos.x.toString() }
        yField.apply { text = targetPos.y.toString() }
        zField.apply { text = targetPos.z.toString() }
    }

    fun setFields(pos: BlockPos){
        xField.text = pos.x.toString()
        yField.text = pos.y.toString()
        zField.text = pos.z.toString()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        Gui.drawRect(width / 2 - 20, height / 2 - 20, width / 2 + 20, height / 2 + 20, 0xffffff)
        drawCenteredString(fontRenderer, "Set Opener Coordinates", width / 2, height / 2 - 15, 0xffffff)
        fields.forEach { it.drawTextBox() }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        fields.forEach { it.mouseClicked(mouseX, mouseY, mouseButton) }
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        if (Utils.validIntChars.contains(typedChar, true) ||
            Utils.specialCharacterCodes.contains(keyCode)
        ) {
            fields.forEach { it.textboxKeyTyped(typedChar, keyCode) }

        // If W or S are pressed, increment/decrement focused coord
        } else if (Utils.keyForward.isActiveAndMatches(keyCode) || Utils.keyBack.isActiveAndMatches(keyCode)){
            val focused = fields.firstOrNull { it.isFocused }
            var fieldInt = focused?.text?.toIntOrNull() ?: return

            if (Utils.keyForward.isActiveAndMatches(keyCode))
                fieldInt++
            else if (Utils.keyBack.isActiveAndMatches(keyCode))
                fieldInt--

            focused.text = fieldInt.toString()
        } else if (keyCode == Utils.CODE_TAB){
            val focusedIdx = fields.indexOfFirst { it.isFocused }
            fields.forEach { it.isFocused = false }
            val newIdx = if (focusedIdx + 1 > 2) 0 else focusedIdx + 1
            fields[newIdx].isFocused = true
        } else if (Utils.keyInv.isActiveAndMatches(keyCode)) {
            this.mc.player.closeScreen()
        }
    }

    override fun onGuiClosed() {
        val intFields = fields.map { it.text.toIntOrNull() ?: Int.MIN_VALUE}
        val posLT = BlockPos(intFields[0], intFields[1], intFields[2])
        PacketHandler.INSTANCE.sendToServer(CoordsMessage(openerPos, posLT))
        super.onGuiClosed()
    }
}