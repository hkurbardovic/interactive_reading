package com.hkurbardovic.interactivereading.utilities

import android.app.Activity
import android.graphics.Point
import android.view.Display

object CoreUtils {

    fun getScreenWidth(activity: Activity): Int {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        return size.x
    }

    fun getScreenHeight(activity: Activity): Int {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        return size.y
    }
}