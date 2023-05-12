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
import javax.swing.SwingUtilities
import javax.swing.event.MouseInputAdapter
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Scene(val parameter: Parameters) : JPanel() {
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
                savedThetaX = parameter.thetaX
                savedThetaY = parameter.thetaY
                savedThetaZ = parameter.thetaZ
            }

            override fun mouseDragged(e: MouseEvent) {
                super.mouseDragged(e)

                if (SwingUtilities.isLeftMouseButton(e)) {
                    logger.info { "Перетаскивание левой мышкой" }
                    parameter.thetaZ = (savedThetaZ + (startedX - e.x) * 0.03)
                    parameter.thetaX = (savedThetaX + (startedY - e.y) * -0.03)
                } else {
                    logger.debug { "Перетаскивание $e" }
                }
                repaint()
            }

            override fun mouseWheelMoved(e: MouseWheelEvent) {
                super.mouseWheelMoved(e)
                parameter.fov += e.preciseWheelRotation * 0.01
                repaint()
            }
        }

        addMouseListener(mouseAdapter)
        addMouseMotionListener(mouseAdapter)
        addMouseWheelListener(mouseAdapter)
    }

    private fun drawLine(g2: Graphics2D, matrixA: Matrix, matrixB: Matrix, width: Int, height: Int) {
        val x1 = ((matrixA[0, 0] + 1.0) * width / 2.0).toInt()
        val y1 = ((matrixA[0, 1] + 1.0) * height / 2.0).toInt()
        val x2 = ((matrixB[0, 0] + 1.0) * width / 2.0).toInt()
        val y2 = ((matrixB[0, 1] + 1.0) * height / 2.0).toInt()
        g2.drawLine(
            x1, y1,
            x2, y2
        )
    }

    private fun drawFigure(g2: Graphics2D) {
        val a = height.toDouble() / width
        val f = 1.0 / tan(parameter.fov / 2)

        val q = parameter.far / (parameter.far - parameter.near)
        val clipMatrix = Matrix(
            arrayOf(
                doubleArrayOf(a * f, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, f, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, q, 1.0),
                doubleArrayOf(0.0, 0.0, (-parameter.near * q), 0.0)
            )
        )

        val rotZ = Matrix(
            arrayOf(
                doubleArrayOf(cos(parameter.thetaZ), sin(parameter.thetaZ), 0.0, 0.0),
                doubleArrayOf(-sin(parameter.thetaZ), cos(parameter.thetaZ), 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rotX = Matrix(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, cos(parameter.thetaX), sin(parameter.thetaX), 0.0),
                doubleArrayOf(0.0, -sin(parameter.thetaX), cos(parameter.thetaX), 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rotY = Matrix(
            arrayOf(
                doubleArrayOf(cos(parameter.thetaY), 0.0, sin(parameter.thetaY), 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(-sin(parameter.thetaY), 0.0, cos(parameter.thetaY), 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0)
            )
        )
        val rot = rotX * rotY * rotZ
        g2.color = Config.BACKGROUND_COLOR
        g2.fillRect(0, 0, width, height)
        g2.color = Config.SPLINE_COLOR
        for (line in parameter.points) {
            val A = line[0] * rot * clipMatrix
            val B = line[1] * rot * clipMatrix
            drawLine(g2, A, B, width, height)
        }
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        drawFigure(g2)
    }
}

