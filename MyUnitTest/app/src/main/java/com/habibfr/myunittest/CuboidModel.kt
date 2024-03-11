package com.habibfr.myunittest

import android.health.connect.datatypes.units.Length

class CuboidModel {
    private var width = 0.0
    private var length = 0.0
    private var height = 0.0

    fun getVolume(): Double = width * height * length

    fun getSurfaceArea(): Double {
        val wl = width * length
        val wh = width * height
        val lh = length * height

        return 2 * (wl + wh + lh)
    }

    fun getCircumFerence(): Double = 4 * (width + length + height)

    fun save(width: Double, length: Double, height: Double) {
        this.width = width
        this.length = length
        this.height = height
    }
}