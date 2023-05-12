package ru.nsu.ccfit.melon.app.tool


import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.app.SettingFrame
import ru.nsu.ccfit.melon.core.Tool
import java.io.File


object SceneSettingTool : Tool(
    name = "Edit parameters"
) {
    override fun execute(context: Context) {
        val frame = SettingFrame(context)
        frame.isVisible = true
    }

    override val tooltip = "Редактирование параметров"
}


