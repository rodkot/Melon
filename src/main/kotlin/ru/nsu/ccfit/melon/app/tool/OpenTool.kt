package ru.nsu.ccfit.melon.app.tool

import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.core.Tool
import java.io.File


object OpenTool : Tool(
    name = "Open"
) {

    override fun execute(context: Context) {
        TODO("Реализовать")
    }

    override val tooltip = "Отрыть сцену"
}
