package com.hkurbardovic.interactivereading.base

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.models.Data
import com.hkurbardovic.interactivereading.models.ScaleImage
import com.hkurbardovic.interactivereading.utilities.CoreUtils
import dagger.android.support.DaggerAppCompatActivity
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (InteractiveReading.INSTANCE.getData() == null) setData()
        if (InteractiveReading.INSTANCE.getScaleImage() == null) createScaleImage()
    }

    private fun setData() {
        val jsonString = readDataFromJson()
        val data = try {
            Gson().fromJson(
                jsonString,
                Data::class.java
            )
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            return
        }

        InteractiveReading.INSTANCE.setData(data)
    }

    private fun readDataFromJson(): String? {
        return try {
            val `is`: InputStream = assets.open("data.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    private fun createScaleImage() {
        val screenWidth = CoreUtils.getScreenWidth(this).toDouble()
        val screenHeight = CoreUtils.getScreenHeight(this).toDouble()

        val imageScale = IMAGE_HEIGHT / IMAGE_WIDTH
        val screenScale = screenHeight / screenWidth

        if (imageScale > screenScale) {
            InteractiveReading.INSTANCE.setScaleImage(
                ScaleImage(
                    imageWidth = screenWidth * imageScale,
                    imageHeight = screenHeight,
                    scale = screenScale
                )
            )
        }
    }

    companion object {
        private const val IMAGE_WIDTH = 724.0
        private const val IMAGE_HEIGHT = 512.0
    }
}