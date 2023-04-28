import java.lang.Thread.currentThread
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        val frame = JFrame("Melon")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(400, 300)
        frame.isVisible = true

        val iconUrl = currentThread().contextClassLoader.getResource("icon.png")
        val icon = ImageIcon(iconUrl)
        frame.iconImage = icon.image
    }

}