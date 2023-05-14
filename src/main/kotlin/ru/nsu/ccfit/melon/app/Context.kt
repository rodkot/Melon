package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.app.tool.AboutTool
import ru.nsu.ccfit.melon.app.tool.OpenTool
import ru.nsu.ccfit.melon.app.tool.SaveTool
import ru.nsu.ccfit.melon.app.tool.SceneSettingTool
import javax.swing.JFrame

object Context {
    var parameters = Parameters()

    var scene = Scene()

    private var toolList = listOf(
        OpenTool,
        SaveTool,
        SceneSettingTool,
        AboutTool
    )
    var mainFrame: JFrame = MainWindow(
        scene = scene,
        tools = toolList,
        width = Config.SIZE_WINDOW.first,
        height = Config.SIZE_WINDOW.second,
        title = Config.NAME_APP
    )

}
