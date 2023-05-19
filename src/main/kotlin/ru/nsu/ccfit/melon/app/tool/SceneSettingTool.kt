package ru.nsu.ccfit.melon.app.tool


import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.app.SettingDialog
import ru.nsu.ccfit.melon.core.Tool

object SceneSettingTool : Tool(
    name = "Параметры"
) {
    override fun execute(context: Context) {
        val dialog = SettingDialog(context)
        dialog.isVisible = true
    }

    override val tooltip = "Редактирование параметров"
}


