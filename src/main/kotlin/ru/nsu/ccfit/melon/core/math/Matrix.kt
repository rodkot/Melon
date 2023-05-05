package ru.nsu.ccfit.melon.core.math

import java.io.Serializable


class Matrix : Serializable {

    private val M: Int

    private val N: Int
    private val data: Array<DoubleArray>

    constructor(data: Array<DoubleArray>) {
        M = data.size
        N = data[0].size
        this.data = Array(M) { DoubleArray(N) }
        for (i in 0 until M) {
            System.arraycopy(data[i], 0, this.data[i], 0, N)
        }
    }

    /**
     * Creates a vector (horizontal)
     *
     * @param data vector to use
     */
    constructor(data: DoubleArray) {
        M = 1
        N = data.size
        this.data = Array(M) { DoubleArray(N) }
        System.arraycopy(data, 0, this.data[0], 0, N)
    }

    constructor(M: Int, N: Int) {
        this.M = M
        this.N = N
        data = Array(M) { DoubleArray(N) }
    }

    fun transpose(): Matrix {
        val A = Matrix(N, M)
        for (i in 0 until M) {
            for (j in 0 until N) {
                A.data[j][i] = data[i][j]
            }
        }
        return A
    }

    operator fun times(B: Matrix): Matrix {
        val A = this
        if (A.N != B.M) {
            throw RuntimeException("Illegal matrix dimensions: " + A.N + " != " + B.M)
        }
        val C = Matrix(A.M, B.N)
        for (i in 0 until C.M) {
            for (j in 0 until C.N) {
                for (k in 0 until A.N) {
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
        for (i in 0 until M) {
            for (j in 0 until N) {
                System.out.printf("%5.2f ", data[i][j])
            }
            println()
        }
    }
}
