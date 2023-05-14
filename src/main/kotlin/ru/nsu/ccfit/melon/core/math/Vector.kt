package ru.nsu.ccfit.melon.core.math

class Vector(data: DoubleArray) : Matrix(arrayOf(data))

fun max(vectors: List<Vector>): Vector {
    return Vector(doubleArrayOf(0.0, 0.0, 0.0, 1.0))
}