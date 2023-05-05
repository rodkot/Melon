package ru.nsu.ccfit.melon.app.tool

import ru.nsu.ccfit.melon.app.Context
import ru.nsu.ccfit.melon.core.Tool
import java.io.File


class OpenTool : Tool() {
    override val name: String
        get() = "Open"
    override val iconPath: File
        get() = File("open.gif")
    override val menuPath: String
        get() = "File/Open"

    override fun execute(context: Context) {
//        val file: Unit = getOpenFileName(context.getMainFrame(), arrayOf<String>("wirescene"), "Wireframe scene file")
//        if (file == null) {
//            println("File not selected")
//            return
//        }
//        try {
//            FileInputStream(file.getFile().getAbsolutePath()).use { fos ->
//                ObjectInputStream(fos).use { ois ->
//                    val sceneParameters: SceneParameters = ois.readObject() as SceneParameters
//                    context.setSceneParameters(sceneParameters)
//                    context.getScene().updateParameters(sceneParameters)
//                }
//            }
//        } catch (ex: IOException) {
//            JOptionPane.showMessageDialog(
//                context.getMainFrame(),
//                "Selected file " + file.getFile().getName() + " can not be opened."
//            )
//            ex.printStackTrace()
//        } catch (ex: ClassNotFoundException) {
//            JOptionPane.showMessageDialog(
//                context.getMainFrame(),
//                "Selected file " + file.getFile().getName() + " can not be opened."
//            )
//            ex.printStackTrace()
//        }
    }

    override val tooltip = "Open an image"
}
