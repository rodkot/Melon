package ru.nsu.ccfit.melon.core

import ru.nsu.ccfit.melon.app.SettingFrame
import ru.nsu.ccfit.melon.app.tool.OpenTool
import ru.nsu.ccfit.melon.app.tool.SceneSettingTool

object ToolManager {
    var toolList = listOf(
        OpenTool(),
        SceneSettingTool()
    )
}

