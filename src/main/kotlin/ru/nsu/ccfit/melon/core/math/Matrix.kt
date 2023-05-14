package ru.nsu.ccfit.melon.core.math

import java.io.Serializable


open class Matrix : Serializable {

    private val matrixM: Int

    private val matrixN: Int
    private val data: Array<DoubleArray>

    constructor(data: Array<DoubleArray>) {
        matrixM = data.size
        matrixN = data[0].size
        this.data = Array(matrixM) { DoubleArray(matrixN) }
        for (i in 0 until matrixM) {
            System.arraycopy(data[i], 0, this.data[i], 0, matrixN)
        }
    }

    constructor(M: Int, N: Int) {
        this.matrixM = M
        this.matrixN = N
        data = Array(M) { DoubleArray(N) }
    }

    fun transpose(): Matrix {
        val A = Matrix(matrixN, matrixM)
        for (i in 0 until matrixM) {
            for (j in 0 until matrixN) {
                A.data[j][i] = data[i][j]
            }
        }
        return A
    }

    operator fun times(B: Matrix): Matrix {
        val A = this
        if (A.matrixN != B.matrixM) {
            throw RuntimeException("Illegal matrix dimensions: " + A.matrixN + " != " + B.matrixM)
        }
        val C = Matrix(A.matrixM, B.matrixN)
        for (i in 0 until C.matrixM) {
            for (j in 0 until C.matrixN) {
                for (k in 0 until A.matrixN) {
                    C.data[i][j] += A.data[i][k] * B.data.get(k).get(j)
                }
            }
        }
        return C
    }

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }

    fun show() {
        for (i in 0 until matrixM) {
            for (j in 0 until matrixN) {
                System.out.printf("%5.2f ", data[i][j])
            }
            println()
        }
    }
}
