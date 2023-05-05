package ru.nsu.ccfit.melon.app.tool


import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.app.SettingFrame
import ru.nsu.ccfit.melon.core.Tool
import java.io.File


class SceneSettingTool : Tool() {
    override val name: String
        get() = "Edit scene parameters"
    override val iconPath: File
        get() = File("edit.png")
    override val menuPath: String
        get() = "Scene/Edit"

    override fun execute(context: Context) {
        val frame = SettingFrame(context)
        frame.isVisible = true
    }
}


