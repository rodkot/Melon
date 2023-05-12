package ru.nsu.ccfit.melon.core

import ru.nsu.ccfit.melon.app.Context
import java.io.File
import java.util.*


abstract class Tool(
    val name: String
) {

    abstract fun execute(context: Context)
    open val tooltip = "Использование " + name.lowercase(Locale.getDefault())
}

