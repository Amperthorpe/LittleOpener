package com.alleluid.littleopener

import net.minecraftforge.common.config.Configuration
import java.io.File

object ConfigHandler {
    var config: Configuration? = null

    var maxDistance: Int? = null
    var isButton: Boolean? = null

    fun init(configFile: File) {
        if (config == null){
            config = Configuration(configFile)
            load()
        }
    }

    fun load(){
        maxDistance = config?.getInt("maxDistance", "General", 25, 0, Int.MAX_VALUE,
                "Maximum distance between LittleTiles and opener blocks. 0 for no limit.")
        isButton = config?.getBoolean("isButton", "General", true,
                "Should opener act as a button, activating on right click?")

        if (config?.hasChanged() == true) config!!.save()
    }

}