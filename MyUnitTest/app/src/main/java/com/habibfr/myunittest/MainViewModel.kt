package com.habibfr.myunittest

class MainViewModel(private val cuboidModel: CuboidModel) {
    fun getCircumference() = cuboidModel.getCircumFerence()
    fun getSurfaceAre() = cuboidModel.getSurfaceArea()
    fun getVolume() = cuboidModel.getVolume()

    fun save(w: Double,l: Double, h: Double) {
        cuboidModel.save(w, l, h)
    }

}