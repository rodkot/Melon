package ru.nsu.ccfit.melon.app.tool

import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.core.Tool
import ru.nsu.ccfit.melon.core.file.FileWithExtension
import ru.nsu.ccfit.melon.core.file.getSaveFileName
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream


object SaveTool : Tool(
    name = "Сохранение"
) {

    override fun execute(context: Context) {
        var fileWithExtension = getSaveFileName(context.mainFrame)
        if (fileWithExtension == null) {
          return
        } else {
            if (fileWithExtension.extension == null) {
                fileWithExtension = FileWithExtension(File(fileWithExtension.file.absolutePath + ".wirescene"), "wireframe")
            }
            try {
                FileOutputStream(fileWithExtension.file.absolutePath).use { fos ->
                    ObjectOutputStream(fos).use { oos ->
                        oos.writeObject(
                            context.parameters
                        )
                    }
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }




    override val tooltip = "Сохранить сцену"
}


