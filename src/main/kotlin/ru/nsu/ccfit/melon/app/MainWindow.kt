package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.core.Tool
import ru.nsu.ccfit.melon.core.ToolManager
import ru.nsu.ccfit.melon.core.ui.MainFrame
import java.awt.Dimension


class MainWindow(private val width: Int, private val height: Int, private val title: String) :
    MainFrame(width, height, title) {
    private var ctx: Context
    private var scene: Scene

    /**
     * Default constructor to create main window
     */
    init {
        minimumSize = Dimension(width, height)

        scene = Scene()
        ctx = Context(this, SceneParameters, scene)
        add(scene)
        try {
            for (tool in ToolManager.toolList) {
                addToolMenu(tool)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun addToolMenu(tool: Tool) {
        //  addMenuItem(tool.menuPath, tool.tooltip, 0, tool.iconPath) {  applyTool(tool) }
          addToolBarButton(tool.menuPath){ applyTool(tool) }
    }




    private fun applyTool(tool: Tool) {
        try {
            tool.execute(ctx)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}