package com.hkurbardovic.interactivereading.polygon

class Polygon(
    private val sides: List<Line>,
    private val boundingBox: BoundingBox?,
    val id: String,
    val display: String
) {

    class Builder {
        private val vertexes = arrayListOf<Point>()
        private val sides = arrayListOf<Line>()
        private var boundingBox: BoundingBox? = null

        private var firstPoint = true
        private var isClosed = false

        fun addVertex(point: Point): Builder {
            if (isClosed) {
                isClosed = false
            }

            updateBoundingBox(point)
            vertexes.add(point)

            if (vertexes.size > 1) {
                sides.add(Line(vertexes[vertexes.size - 2], point))
            }

            return this
        }

        fun addVertexes(coordinates: List<Int?>): Builder {
            if (isClosed) {
                isClosed = false
            }

            for ((i) in coordinates.withIndex()) {
                if (i == 0) continue

                if (i % 2 != 0) {
                    val x = coordinates[i-1] ?: continue
                    val y =  coordinates[i] ?:continue
                    val point = Point(x.toDouble(), y.toDouble())

                    updateBoundingBox(point)
                    vertexes.add(point)

                    if (vertexes.size > 1) {
                        sides.add(Line(vertexes[vertexes.size - 2], point))
                    }
                }
            }

            return this
        }

        fun close(): Builder? {
            validate()

            sides.add(Line(vertexes[vertexes.size - 1], vertexes[0]))
            isClosed = true
            return this
        }

        fun build(id: String, display: String): Polygon {
            validate()

            if (!isClosed) {
                sides.add(Line(vertexes[vertexes.size - 1], vertexes[0]))
            }

            return Polygon(sides, boundingBox, id, display)
        }

        private fun updateBoundingBox(point: Point) {
            if (firstPoint) {
                boundingBox = BoundingBox()
                boundingBox?.apply {
                    xMax = point.x
                    xMin = point.x
                    yMax = point.y
                    yMin = point.y
                }

                firstPoint = false
            } else {
                boundingBox?.let {
                    if (point.x > it.xMax) {
                        it.xMax = point.x
                    } else if (point.x < it.xMin) {
                        it.xMin = point.x
                    }
                    if (point.y > it.yMax) {
                        it.yMax = point.y
                    } else if (point.y < it.yMin) {
                        it.yMin = point.y
                    }
                }
            }
        }

        private fun validate() {
            if (vertexes.size < 3) {
                throw RuntimeException("Polygon must have at least 3 points")
            }
        }
    }

    operator fun contains(point: Point): Boolean {
        if (inBoundingBox(point)) {
            val ray: Line? = createRay(point)
            var intersection = 0
            for (side in sides) {
                if (intersect(ray, side)) {
                    intersection++
                }
            }

            if (intersection % 2 != 0) {
                return true
            }
        }
        return false
    }

    private fun intersect(ray: Line?, side: Line): Boolean {
        if (ray == null) return false

        val intersectPoint = if (!ray.vertical && !side.vertical) {
            if (ray.a - side.a == 0.0) {
                return false
            }
            val x: Double = (side.b - ray.b) / (ray.a - side.a)
            val y: Double = side.a * x + side.b
            Point(x, y)
        } else if (ray.vertical && !side.vertical) {
            val x: Double = ray.start.x
            val y: Double = side.a * x + side.b
            Point(x, y)
        } else if (!ray.vertical && side.vertical) {
            val x: Double = side.start.x
            val y: Double = ray.a * x + ray.b
            Point(x, y)
        } else {
            return false
        }

        return side.isInside(intersectPoint) && ray.isInside(intersectPoint)
    }

    private fun createRay(point: Point): Line? {
        if (boundingBox == null) return null
        val epsilon: Double = (boundingBox.xMax - boundingBox.xMin) / 10e6
        val outsidePoint = Point(boundingBox.xMin - epsilon, boundingBox.yMin)
        return Line(outsidePoint, point)
    }

    private fun inBoundingBox(point: Point): Boolean {
        if (boundingBox == null) return false
        return !(point.x < boundingBox.xMin || point.x > boundingBox.xMax || point.y < boundingBox.yMin || point.y > boundingBox.yMax)
    }
}