package ru.nsu.ccfit.melon.core.math

import ru.nsu.ccfit.melon.core.Point2D

class BSpline(private val points: Array<Point2D>) {

    private val matrixM_s = Matrix(
        arrayOf(
            doubleArrayOf(-1.0 / 6, 3.0 / 6, -3.0 / 6, 1.0 / 6),
            doubleArrayOf(3.0 / 6, -6.0 / 6, 3.0 / 6, 0.0 / 6),
            doubleArrayOf(-3.0 / 6, 0.0 / 6, 3.0 / 6, 0.0 / 6),
            doubleArrayOf(1.0 / 6, 4.0 / 6, 1.0 / 6, 0.0 / 6)
        )
    )

    /**
     * Получение точек сплайма
     * @param n - количество
     */
    fun getPoints(n: Int): Array<Point2D> {
        if (points.size < 4) {
            return arrayOf()
        }
        val xPoints = DoubleArray(4)
        val yPoints = DoubleArray(4)
        val upto = points.size - 3
        val totalPoints = Array((n + 1) * upto) { Point2D() }
        for (i in 1 until upto + 1) {
            for (cnt in 0..3) {
                xPoints[cnt] = points[i + cnt - 1].x
                yPoints[cnt] = points[i + cnt - 1].y
            }
            val vectorG_su = Vector(xPoints).transpose()
            val vectorG_sv = Vector(yPoints).transpose()
            for (j in 0..n) {
                val t = j * 1.0 / n
                val vectorT = Vector(doubleArrayOf(t * t * t, t * t, t, 1.0))
                val uPoints = vectorT * matrixM_s * vectorG_su
                val vPoints = vectorT * matrixM_s * vectorG_sv
                totalPoints[(i - 1) * (n + 1) + j] = Point2D(
                    uPoints[0, 0],
                    vPoints[0, 0]
                )
            }
        }
        return totalPoints
    }
}

