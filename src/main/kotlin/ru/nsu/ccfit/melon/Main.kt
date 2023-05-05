package ru.nsu.ccfit.melon

import ru.nsu.ccfit.melon.app.MainWindow
import java.lang.Thread.currentThread
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        val window = MainWindow(400, 400, "Melon")
        window.isVisible = true
    }

}