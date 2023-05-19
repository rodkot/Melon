package ru.nsu.ccfit.melon.core.math

import ru.nsu.ccfit.melon.core.Point2D

class Vector(data: DoubleArray) : Matrix(arrayOf(data)){
    val x: Double
        get()= this[0, 0]

    val y: Double
        get()= this[0, 1]

    val z: Double
        get()= this[0, 2]
}

fun  List<Vector>.max(req: (Vector, Vector) -> Boolean): Vector {
    var max = this[0]
    for (i in this.indices) {
        if (req.invoke(max, this[i])) {
            max = this[i]
        }
    }
    return max
}

