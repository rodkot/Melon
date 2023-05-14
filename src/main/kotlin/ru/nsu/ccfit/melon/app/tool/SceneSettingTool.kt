package ru.nsu.ccfit.melon.app.tool


import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.app.SettingFrame
import ru.nsu.ccfit.melon.core.Tool

object SceneSettingTool : Tool(
    name = "Параметры"
) {
    override fun execute(context: Context) {
        val frame = SettingFrame(context)
        frame.isVisible = true
    }

    override val tooltip = "Редактирование параметров"
}


