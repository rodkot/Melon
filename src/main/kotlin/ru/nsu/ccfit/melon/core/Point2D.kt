package ru.nsu.ccfit.melon.core

import java.io.Serializable


open class Point2D(
    var x: Double = 0.0,
    var y: Double = 0.0
) : Serializable {


}
fun List<Point2D>.max(req: (Point2D, Point2D) -> Boolean): Point2D {
    var max = this[0]
    for (i in this.indices) {
        if (req.invoke(max, this[i])) {
            max = this[i]
        }
    }
    return max
}

