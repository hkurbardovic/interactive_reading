package com.hkurbardovic.interactivereading.polygon

data class BoundingBox(
    var xMax: Double = Double.NEGATIVE_INFINITY,
    var xMin: Double = Double.NEGATIVE_INFINITY,
    var yMax: Double = Double.NEGATIVE_INFINITY,
    var yMin: Double = Double.NEGATIVE_INFINITY
)