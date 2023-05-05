package ru.nsu.ccfit.melon.core

import ru.nsu.ccfit.melon.app.Context
import java.io.File
import java.util.*


abstract class Tool {
    abstract val name: String
    abstract val iconPath: File
    abstract val menuPath: String

    abstract fun execute(context: Context)
    open val tooltip = "Use " + name.lowercase(Locale.getDefault())
}

