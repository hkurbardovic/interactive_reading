package com.hkurbardovic.interactivereading.polygon

data class Line(
    val start: Point,
    val end: Point,
    var vertical: Boolean = false,
    var a: Double = Double.NaN,
    var b: Double = Double.NaN
) {

    init {
        if (end.x - start.x != 0.0) {
            a = (end.y - start.y) / (end.x - start.x)
            b = start.y - a * start.x
        } else {
            vertical = true
        }
    }

    fun isInside(point: Point): Boolean {
        val maxX: Double = if (start.x > end.x) start.x else end.x
        val minX: Double = if (start.x < end.x) start.x else end.x
        val maxY: Double = if (start.y > end.y) start.y else end.y
        val minY: Double = if (start.y < end.y) start.y else end.y
        return point.x in minX..maxX && point.y >= minY && point.y <= maxY
    }
}