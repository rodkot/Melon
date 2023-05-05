package ru.nsu.ccfit.melon.app

import mu.KotlinLogging
import ru.nsu.ccfit.melon.core.math.Matrix
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import javax.swing.JPanel
import javax.swing.event.MouseInputAdapter
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Scene : JPanel() {
    private val logger = KotlinLogging.logger {}

    init {
        val mouseAdapter: MouseInputAdapter = object : MouseInputAdapter() {
            private var startedX = 0
            private var startedY = 0
            private var savedThetaX = 0.0
            private var savedThetaY = 0.0
            private var savedThetaZ = 0.0
            override fun mousePressed(e: MouseEvent) {
                super.mousePressed(e)
                startedX = e.x
                startedY = e.y
                savedThetaX = SceneParameters.thetaX
                savedThetaY = SceneParameters.thetaY
                savedThetaZ = SceneParameters.thetaZ
            }

            override fun mouseDragged(e: MouseEvent) {
                super.mouseDragged(e)

                if (e.button == MouseEvent.BUTTON1) {
                    logger.info { "Перетаскивание левой мышкой" }
                    SceneParameters.thetaZ = (savedThetaZ + (startedX - e.x) * 0.03)
                    SceneParameters.thetaX = (savedThetaX + (startedY - e.y) * -0.03)
                } else if (e.button == MouseEvent.BUTTON3) {
                    logger.info { "Перетаскивание правой мышкой" }
                    SceneParameters.thetaY = (savedThetaZ + (startedX - e.x) * 0.03)
                    SceneParameters.thetaX = (savedThetaX + (startedY - e.y) * -0.03)
                }else{
                    logger.debug { "Перетаскивание $e" }
                }
                repaint()
            }

            override fun mouseWheelMoved(e: MouseWheelEvent) {
                super.mouseWheelMoved(e)
                SceneParameters.fov += e.preciseWheelRotation * 0.01
                repaint()
            }
        }
        val keyboardAdapter: KeyAdapter = object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                super.keyPressed(e)
                if (e.keyChar.code == KeyEvent.VK_UP) {
                    SceneParameters.thetaX += 0.03
                    repaint()
                } else if (e.keyChar.code == KeyEvent.VK_DOWN) {
                    SceneParameters.thetaX -= 0.03
                    repaint()
                }
            }
        }
        addKeyListener(keyboardAdapter)
        addMouseListener(mouseAdapter)
        addMouseMotionListener(mouseAdapter)
        addMouseWheelListener(mouseAdapter)
    }

    private fun drawLine(g2: Graphics2D, A: Matrix, B: Matrix, width: Int, height: Int) {
        val x1 = ((A[0, 0] + 1.0) * width / 2.0).toInt()
        val y1 = ((A[0, 1] + 1.0) * height / 2.0).toInt()
        val x2 = ((B[0, 0] + 1.0) * width / 2.0).toInt()
        val y2 = ((B[0, 1] + 1.0) * height / 2.0).toInt()
        g2.drawLine(
            x1, y1,
            x2, y2
        )
    }

    private fun drawFigure(g2: Graphics2D) {
        val width = width
        val height = height
        val a = height * 1.0 / width
        val f = 1.0 / tan(SceneParameters.fov / 2)
        val far = SceneParameters.far
        val near = SceneParameters.near
        val q = far / (far - near)
        val clipMatrix = Matrix(
            arrayOf(
                doubleArrayOf(a * f, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, f, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, q, 1.0),
                doubleArrayOf(0.0, 0.0, (-near * q), 0.0)
            )
        )
        val thetaX = SceneParameters.thetaX
        val thetaY = SceneParameters.thetaY
        val thetaZ = SceneParameters.thetaZ
        val rotZ = Matrix(
            arrayOf(
                doubleArrayOf(cos(thetaZ), sin(thetaZ), 0.0, 0.0),
                doubleArrayOf(-sin(thetaZ), cos(thetaZ), 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rotX = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, cos(thetaX), sin(thetaX), 0.0),
                doubleArrayOf(0.0, -sin(thetaX), cos(thetaX), 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rotY = Matrix(
            arrayOf(
                doubleArrayOf(cos(thetaY), 0.0, sin(thetaY), 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(-sin(thetaY), 0.0, cos(thetaY), 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rot = rotX.times(rotY).times(rotZ)
        g2.color = Color.BLACK
        g2.fillRect(0, 0, width, height)
        g2.color = Color.WHITE
        for (line in SceneParameters.points) {
            val A = line[0].times(rot).times(clipMatrix)
            val B = line[1].times(rot).times(clipMatrix)
            drawLine(g2, A, B, width, height)
        }
    }

    private fun drawParams(g2: Graphics2D) {
        g2.drawString(String.format("Theta X: %.0f°", SceneParameters.thetaX), 5, 20)
        g2.drawString(String.format("Theta Y: %.0f°", SceneParameters.thetaY), 5, 40)
        g2.drawString(String.format("Theta Z: %.0f°", SceneParameters.thetaZ), 5, 60)
        g2.drawString(String.format("FOV: %.0f° (%.2f)", SceneParameters.degFov, SceneParameters.fov), 5, 80)
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        drawFigure(g2)
        drawParams(g2)
    }
}

