package ru.nsu.ccfit.melon.app.tool

import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.core.Tool
import javax.swing.JOptionPane

object AboutTool : Tool(name = "О Программе") {
    override fun execute(context: Context) {
        JOptionPane.showMessageDialog(
            context.mainFrame,
            """
                        Melon
                        Лабораторная работа 4 по курсу
                        "Компьютерная и инженерная графика"
                        Выполнена студентом ФИТ:
                        Котов Родион 20208
                        Май,2023   """,
            name, JOptionPane.INFORMATION_MESSAGE
        )

    }
}