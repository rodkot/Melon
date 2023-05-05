package ru.nsu.ccfit.melon.core.math

import ru.nsu.ccfit.melon.core.Point2D


class BSpline(points: Array<Point2D>) {
    private val points: Array<Point2D>

    init {
        this.points = points
    }

    fun getSplinePoints(N: Int): Array<Point2D> {
        if (points.size < 4) {
            return arrayOf<Point2D>()
        }
        val xPoints = DoubleArray(4)
        val yPoints = DoubleArray(4)
        val upto = points.size - 3
        val totalPoints = Array((N + 1) * upto){Point2D()}
        for (i in 1 until upto + 1) {
            for (cnt in 0..3) {
                xPoints[cnt] = points[i + cnt - 1].x
                yPoints[cnt] = points[i + cnt - 1].y
            }
            val G_su: Matrix = Matrix(xPoints).transpose()
            val G_sv: Matrix = Matrix(yPoints).transpose()
            for (j in 0..N) {
                val t = j * 1.0 / N
                val T = Matrix(doubleArrayOf(t * t * t, t * t, t, 1.0))
                val uPoints = T
                    .times(M_s)
                    .times(G_su)
                val vPoints = T.times(M_s).times(G_sv)
                totalPoints[(i - 1) * (N + 1) + j] = Point2D(
                    uPoints[0, 0],
                    vPoints[0, 0]
                )
            }
        }
        return totalPoints
    }

    companion object {
        private val M_s = Matrix(
            arrayOf(
                doubleArrayOf(-1.0 / 6, 3.0 / 6, -3.0 / 6, 1.0 / 6),
                doubleArrayOf(3.0 / 6, -6.0 / 6, 3.0 / 6, 0.0 / 6),
                doubleArrayOf(-3.0 / 6, 0.0 / 6, 3.0 / 6, 0.0 / 6),
                doubleArrayOf(1.0 / 6, 4.0 / 6, 1.0 / 6, 0.0 / 6)
            )
        )
    }
}

