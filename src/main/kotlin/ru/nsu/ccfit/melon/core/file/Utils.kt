package ru.nsu.ccfit.melon.core.file

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame

data class FileWithExtension(
    val file: File,
    val extension: String?
)

fun getOpenFileName(parent: JFrame): FileWithExtension? {
    val fileChooser = JFileChooser()


    if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
        val f = fileChooser.selectedFile
        return FileWithExtension(f, getExtension(f.name))
    }
    return null
}


fun getSaveFileName(
    parent: JFrame
): FileWithExtension? {
    val fileChooser = JFileChooser()
//        val filter: FileFilter = ExtensionFileFilter(extensions, description)
//        fileChooser.addChoosableFileFilter(filter)
    //   fileChooser.currentDirectory = cg.FileUtils.getDataDirectory()
    if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
        val f = fileChooser.selectedFile
        return FileWithExtension(f, getExtension(f.name))
    }
    return null
}

fun getExtension(filename: String): String? {
    val parts = filename.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return if (parts.size == 1) {
        null
    } else parts[parts.size - 1]
}
