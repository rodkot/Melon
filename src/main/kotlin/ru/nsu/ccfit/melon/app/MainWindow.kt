package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.core.Tool
import ru.nsu.ccfit.melon.core.ui.PlumFrame
import java.awt.Dimension


class MainWindow(
    scene: Scene,
    tools: List<Tool>,
    width: Int,
    height: Int,
    title: String
) :
    PlumFrame(width = width, height = height, title = title) {
    private val context = Context

    init {
        minimumSize = Dimension(width, height)

        add(scene)
        try {
            for (tool in tools) {
                addToolMenu(tool)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun addToolMenu(tool: Tool) {
        addToolBarButton(tool.name) { applyTool(tool) }
    }

    private fun applyTool(tool: Tool) {
        try {
            tool.execute(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}