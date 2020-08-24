package com.hkurbardovic.interactivereading.app

import com.hkurbardovic.interactivereading.di.DaggerAppComponent
import com.hkurbardovic.interactivereading.models.Data
import com.hkurbardovic.interactivereading.models.ScaleImage
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class InteractiveReading : DaggerApplication() {

    private var scaleImage: ScaleImage? = null
    private var data: Data? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

    fun setScaleImage(scaleImage: ScaleImage) {
        this.scaleImage = scaleImage
    }

    fun getScaleImage() = scaleImage

    fun setData(data: Data) {
        this.data = data
    }

    fun getData() = data

    companion object {
        lateinit var INSTANCE: InteractiveReading
    }
}