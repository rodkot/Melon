package ru.nsu.ccfit.melon.app

import ru.nsu.ccfit.melon.core.Point2D
import ru.nsu.ccfit.melon.core.math.Matrix

import java.io.Serializable


object SceneParameters : Serializable {
    var splineBasePoints = listOf<Point2D>()
    var points = listOf<Array<Matrix>>()
    var fov = 60.0 * (Math.PI / 180.0)
    var far = 10
    var near = 0.1
    var angleN = 10
    var virtualAngleN = 3
    var splineN = 3
    var thetaX = 0.0

    var thetaY = 0.0

    var thetaZ = 0.0


    private fun degToRad(deg: Double): Double {
        return deg * (Math.PI / 180.0)
    }

    private fun radToDeg(rad: Double): Double {
        return rad * (180.0 / Math.PI)
    }

    var degFov: Double
        get() = radToDeg(fov)
        set(degFov) {
            fov = (degToRad(degFov))
        }
    val degThetaX: Double
        get() = radToDeg(thetaX)
    val degThetaY: Double
        get() = radToDeg(thetaY)
    val degThetaZ: Double
        get() = radToDeg(thetaZ)

}
