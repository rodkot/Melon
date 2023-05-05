package ru.nsu.ccfit.melon.app

import mu.KotlinLogging
import ru.nsu.ccfit.melon.app.Config.AXIS_COLOR
import ru.nsu.ccfit.melon.app.Config.BACKGROUND_COLOR
import ru.nsu.ccfit.melon.app.Config.POINT_COLOR
import ru.nsu.ccfit.melon.app.Config.POINT_RADIUS
import ru.nsu.ccfit.melon.app.Config.SPLINE_COLOR
import ru.nsu.ccfit.melon.core.Point2D
import ru.nsu.ccfit.melon.core.math.BSpline
import ru.nsu.ccfit.melon.core.math.Matrix
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.JPanel
import javax.swing.event.MouseInputAdapter
import kotlin.math.max
import kotlin.math.sqrt


class PointsPanel(private val sceneParameters: SceneParameters) : JPanel() {
    private val logger = KotlinLogging.logger {}

    inner class PointDragListener() : MouseInputAdapter() {
        private var currentPoint: MovablePoint2D? = null
        private var pressedX = 0
        private var pressedY = 0
        private var savedOffsetX = 0
        private var savedOffsetY = 0
        override fun mouseClicked(e: MouseEvent) {}
        override fun mousePressed(e: MouseEvent) {
            if (e.button == MouseEvent.BUTTON1) {
                currentPoint = findPoint(e.x, e.y)
                if (currentPoint == null) {
                    addPoint(e.x, e.y)
                    repaint()
                }
            } else if (e.button == MouseEvent.BUTTON3) {
                val toRemovePoint: MovablePoint2D? = findPoint(e.x, e.y)
                if (toRemovePoint != null) {
                    removePoint(toRemovePoint)
                    repaint()
                } else {
                    pressedX = e.x
                    pressedY = e.y
                    savedOffsetX = offsetX
                    savedOffsetY = offsetY
                }
            }
        }

        override fun mouseReleased(e: MouseEvent) {
            currentPoint = null
        }

        override fun mouseDragged(e: MouseEvent) {
            if (e.button == MouseEvent.BUTTON1 && currentPoint != null) {
                currentPoint?.imageX = e.x
                currentPoint?.imageY = e.y
                repaint()
            } else if (e.button == MouseEvent.BUTTON3) {
                offsetX = savedOffsetX + e.x - pressedX
                offsetY = savedOffsetY + e.y - pressedY
                repaint()
            }
        }
    }

    var listener: PointsPanel.PointDragListener
    private var offsetX = 0
    private var offsetY = 0

    private inner class MovablePoint2D : Point2D {
        constructor(x: Double, y: Double) : super(x, y)
        constructor(imageX: Int, imageY: Int) : super(0.0, 0.0) {
            this.imageX = imageX
            this.imageY = imageY
        }

        var imageX: Int
            get() = ((x + 0.5) * width).toInt() + offsetX
            set(imageX) {
                x = ((imageX - offsetX - width / 2.0) / width)
            }
        var imageY: Int
            get() = ((y + 0.5) * height).toInt() + offsetY
            set(imageY) {
                y = ((imageY - offsetY - height / 2.0) / height)
            }

        fun distanceTo(p: MovablePoint2D): Double {
            return sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y))
        }

        fun imageDistanceTo(p: MovablePoint2D): Double {
            return sqrt(((imageX - p.imageX) * (imageX - p.imageX) + (imageY - p.imageY) * (imageY - p.imageY)).toDouble())
        }
    }

    private val points: MutableList<MovablePoint2D>

    init {
        points = ArrayList<MovablePoint2D>()
        for (point in sceneParameters.splineBasePoints) {
            points.add(MovablePoint2D(point.x, point.y))
        }
        background = BACKGROUND_COLOR
        listener = PointDragListener()
        addMouseListener(listener)
        addMouseMotionListener(listener)
    }

    fun addPoint() {
        points.add(MovablePoint2D(0.5, 0.5))
        repaint()
    }

    fun addPoint(imageX: Int, imageY: Int) {
        val newPoint: MovablePoint2D = MovablePoint2D(imageX, imageY)
        if (points.isEmpty()) {
            points.add(newPoint)
        } else {
            val pFirst: MovablePoint2D = points[0]
            val pLast: MovablePoint2D = points[points.size - 1]
            if (newPoint.distanceTo(pFirst) < newPoint.distanceTo(pLast)) {
                points.add(0, newPoint)
            } else {
                points.add(newPoint)
            }
        }
        repaint()
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        drawAxes(g2)
        drawPoints(g2)
        drawSpline(g2)
    }

    private fun drawAxes(g2: Graphics2D) {
        val width = width
        val height = height
        g2.color = AXIS_COLOR
        g2.drawLine(0, height / 2 + offsetY, width, height / 2 + offsetY)
        g2.drawLine(width / 2 + offsetX, 0, width / 2 + offsetX, height)
    }

    private fun drawPoints(g2: Graphics2D) {
        g2.color = POINT_COLOR
        var prev: MovablePoint2D? = null
        for (point in points) {
            g2.drawOval(
                point.imageX - POINT_RADIUS / 2,
                point.imageY - POINT_RADIUS / 2,
                POINT_RADIUS,
                POINT_RADIUS
            )
            if (prev != null) {
                g2.drawLine(
                    point.imageX,
                    point.imageY,
                    prev.imageX,
                    prev.imageY
                )
            }
            prev = point
        }
    }

    private fun drawSpline(g2: Graphics2D) {
        val spline = BSpline(points.toTypedArray())
        val splinePoints = spline.getSplinePoints(sceneParameters.splineN) ?: return
        val width = width
        val height = height
        g2.color = SPLINE_COLOR
        for (i in 1 until splinePoints.size) {
            g2.drawLine(
                (width * (splinePoints[i - 1].x + 0.5)).toInt() + offsetX,
                (height * (splinePoints[i - 1].y + 0.5)).toInt() + offsetY,
                (width * (splinePoints[i].x + 0.5)).toInt() + offsetX,
                (height * (splinePoints[i].y + 0.5)).toInt() + offsetY
            )
        }
    }

    private fun findPoint(imageX: Int, imageY: Int): MovablePoint2D? {
        val click = MovablePoint2D(imageX, imageY)
        for (point in points) {
            if (point.imageDistanceTo(click) <= POINT_RADIUS) {
                return point
            }
        }
        return null
    }

    private fun removePoint(point: MovablePoint2D) {
        points.remove(point)
    }

    val splinePoints: List<Point2D>
        get() {
            val spline = BSpline(points.toTypedArray())
            return spline.getSplinePoints(sceneParameters.splineN).toList()
        }

    val basePoints: List<Point2D>
        get() {
            val res: ArrayList<Point2D> = ArrayList<Point2D>()
            for (point in points) {
                res.add(Point2D(point.x, point.y))
            }
            return res
        }

    private fun findMaxCoordinate(): Double {
        val xMax = splinePoints.stream().max(Comparator.comparingDouble { it.x }).get().x
        val yMax = splinePoints.stream().max(Comparator.comparingDouble { it.y }).get().y
        return max(xMax, yMax)
    }

    val scenePoints: List<Array<Matrix>>
        get() {
            val points2d: List<Point2D> = splinePoints
            val vertices = ArrayList<Array<Matrix>>()
            val angleN = sceneParameters.angleN * sceneParameters.virtualAngleN
            val a = width * 1.0 / height
            val psStep = sceneParameters.splineN
            val k = 1.0
            for (j in 0 until angleN) {
                var i = 0
                while (i < points2d.size) {
                    val p = points2d[i]
                    val Fiv = p.x * k
                    val Fuv = p.y * k
                    vertices.add(
                        arrayOf(
                            Matrix(
                                doubleArrayOf(
                                    Fiv * Math.cos(j * 2 * Math.PI / angleN) * a,
                                    Fiv * Math.sin(j * 2 * Math.PI / angleN) * a,
                                    Fuv,
                                    1.0
                                )
                            ),
                            Matrix(
                                doubleArrayOf(
                                    Fiv * Math.cos((j + 1) % angleN * 2 * Math.PI / angleN) * a,
                                    Fiv * Math.sin((j + 1) % angleN * 2 * Math.PI / angleN) * a,
                                    Fuv,
                                    1.0
                                )
                            )
                        )
                    )
                    if (i + psStep >= points2d.size - 1 && i != points2d.size - 1) {
                        i = points2d.size - 1 - psStep
                    }
                    i += psStep
                }
            }

            val normalAngleN = sceneParameters.angleN

            for (i in 1 until points2d.size) {
                for (j in 0 until normalAngleN) {
                    val p1 = points2d[i]
                    val p2 = points2d[i - 1]
                    vertices.add(
                        arrayOf(
                            Matrix(
                                doubleArrayOf(
                                    p1.x * k * Math.cos(j * 2 * Math.PI / normalAngleN) * a,
                                    p1.x * k * Math.sin(j * 2 * Math.PI / normalAngleN) * a,
                                    p1.y * k,
                                    1.0
                                )
                            ),
                            Matrix(
                                doubleArrayOf(
                                    p2.x * k * Math.cos(j * 2 * Math.PI / normalAngleN) * a,
                                    p2.x * k * Math.sin(j * 2 * Math.PI / normalAngleN) * a,
                                    p2.y * k,
                                    1.0
                                )
                            )
                        )
                    )
                }
            }
            return vertices
        }

    fun reset() {
        points.clear()
        repaint()
    }
}

