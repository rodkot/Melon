package ru.nsu.ccfit.melon.app.tool

import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.app.Parameters
import ru.nsu.ccfit.melon.core.Tool
import ru.nsu.ccfit.melon.core.file.getOpenFileName
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import javax.swing.JOptionPane


object OpenTool : Tool(
    name = "Открыть"
) {

    override fun execute(context: Context) {
        val fileWithExtension = getOpenFileName(context.mainFrame) ?: return
        try {
            FileInputStream(fileWithExtension.file.absolutePath).use { fos ->
                ObjectInputStream(fos).use { ois ->
                    val sceneParameters = ois.readObject() as Parameters
                    context.parameters = (sceneParameters)
                }
            }
        } catch (ex: IOException) {
            JOptionPane.showMessageDialog(
                context.mainFrame,
                "Файл " + fileWithExtension.file.name + " имеет неверный формат."
            )
            ex.printStackTrace()
        }
    }

    override val tooltip = "Отрыть сцену"
}

