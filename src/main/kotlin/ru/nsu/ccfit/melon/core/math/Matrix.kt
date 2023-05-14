package ru.nsu.ccfit.melon.core.math

import java.io.Serializable


open class Matrix : Serializable {

    private val m: Int
    private val n: Int

    private val data: Array<DoubleArray>

    constructor(data: Array<DoubleArray>) {
        m = data.size
        n = data[0].size
        this.data = Array(m) { DoubleArray(n) }
        for (i in 0 until m) {
            System.arraycopy(data[i], 0, this.data[i], 0, n)
        }
    }

    constructor(m: Int, n: Int) {
        this.m = m
        this.n = n
        data = Array(m) { DoubleArray(n) }
    }

    fun transpose(): Matrix {
        val matrixA = Matrix(n, m)
        for (i in 0 until m) {
            for (j in 0 until n) {
                matrixA.data[j][i] = data[i][j]
            }
        }
        return matrixA
    }

    operator fun times(B: Matrix): Matrix {
        val matrixA = this
        if (matrixA.n != B.m) {
            throw RuntimeException("Illegal matrix dimensions: " + matrixA.n + " != " + B.m)
        }
        val matrixC = Matrix(matrixA.m, B.n)
        for (i in 0 until matrixC.m) {
            for (j in 0 until matrixC.n) {
                for (k in 0 until matrixA.n) {
                    matrixC.data[i][j] += matrixA.data[i][k] * B.data.get(k).get(j)
                }
            }
        }
        return matrixC
    }

    operator fun get(i: Int, j: Int): Double {
        return data[i][j]
    }
}
