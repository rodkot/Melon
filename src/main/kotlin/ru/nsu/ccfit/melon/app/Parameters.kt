package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.core.Point2D
import ru.nsu.ccfit.melon.core.math.Vector

import java.io.Serializable


class Parameters : Serializable {
    var splineBasePoints = listOf<Point2D>()
    var points = listOf<Array<Vector>>()

    var fov = 40 * (Math.PI / 180.0)
    var far = 10
    var near = 0.1

    var angleN = 10
    var virtualAngleN = 3
    var splineN = 3

    var thetaX = 0.0
    var thetaY = 0.0
    var thetaZ = 0.0
}
