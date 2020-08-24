package com.hkurbardovic.interactivereading.main.repositories

import com.hkurbardovic.interactivereading.models.Book
import com.hkurbardovic.interactivereading.polygon.Polygon
import com.hkurbardovic.interactivereading.utilities.PageDataUtils
import javax.inject.Inject

class MainRepository @Inject constructor() {

    fun getPolygons(book: Book?, page: Int): List<Polygon>? =
        PageDataUtils.getPolygonData(book, page)
}