package ru.nsu.ccfit.melon

import ru.nsu.ccfit.melon.app.Context
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
val context = Context
    SwingUtilities.invokeLater {
        val window = context.mainFrame
        window.isVisible = true
    }

}