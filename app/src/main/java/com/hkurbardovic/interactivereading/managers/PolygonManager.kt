package com.hkurbardovic.interactivereading.managers

import android.view.MotionEvent
import com.hkurbardovic.interactivereading.models.ScaleImage
import com.hkurbardovic.interactivereading.polygon.Point
import com.hkurbardovic.interactivereading.polygon.Polygon
import javax.inject.Inject

class PolygonManager @Inject constructor() {

    fun checkIfInside(
        event: MotionEvent?,
        scaleImage: ScaleImage?,
        polygons: List<Polygon>?
    ): Polygon? {
        if (event == null || polygons == null || scaleImage == null) return null

        val x = event.x * (scaleImage.scale ?: 0.0)
        val y = event.y * (scaleImage.scale ?: 0.0)
        val point = Point(x, y)

        for (polygon in polygons) {
            if (polygon.contains(point)) {
                return polygon
            }
        }

        return null
    }
}