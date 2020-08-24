package com.hkurbardovic.interactivereading.utilities

import com.hkurbardovic.interactivereading.models.Book
import com.hkurbardovic.interactivereading.models.PageMargins
import com.hkurbardovic.interactivereading.models.PageText
import com.hkurbardovic.interactivereading.polygon.Point
import com.hkurbardovic.interactivereading.polygon.Polygon

object PageDataUtils {

    fun getPolygonData(book: Book?, page: Int): List<Polygon>? {
        val pages = book?.pages ?: return null

        if (page > pages.size) return null

        val clickableObjects = pages[page]?.clickableObjects ?: return null

        val builders = arrayListOf<Polygon>()
        for (clickableObject in clickableObjects) {
            val id = clickableObject?.id ?: continue
            val name = clickableObject.name ?: continue
            val coordinates = clickableObject.coordinates ?: continue
            builders.add(Polygon.Builder().addVertexes(coordinates).build(id, name))
        }

        return builders
    }

    fun getTextData(page: Int): List<PageText>? =
        when (page) {
            0 -> getTextData1()
            1 -> getTextData2()
            else -> null
        }

    fun getTextDataString(page: Int): String? =
        when (page) {
            0 -> getTextDataString1()
            1 -> getTextDataString2()
            else -> null
        }

    fun getPageMargins(position: Int) =
        when (position) {
            0 -> PageMargins(10.0, 20.0, 350.0)
            1 -> PageMargins(10.0, 20.0, 500.0)
            else -> null
        }

    private fun getPolygonData1() = listOf(
        Polygon.Builder()
            .addVertex(Point(622.0, 72.0))
            .addVertex(Point(600.0, 104.0))
            .addVertex(Point(664.0, 144.0))
            .addVertex(Point(663.0, 172.0))
            .addVertex(Point(619.0, 206.0))
            .addVertex(Point(659.0, 233.0))
            .addVertex(Point(582.0, 339.0))
            .addVertex(Point(658.0, 376.0))
            .addVertex(Point(658.0, 463.0))
            .addVertex(Point(676.0, 386.0))
            .addVertex(Point(696.0, 461.0))
            .addVertex(Point(694.0, 381.0))
            .addVertex(Point(792.0, 367.0))
            .addVertex(Point(693.0, 226.0))
            .addVertex(Point(696.0, 198.0))
            .addVertex(Point(726.0, 213.0))
            .addVertex(Point(753.0, 142.0))
            .addVertex(Point(741.0, 125.0))
            .addVertex(Point(747.0, 97.0))
            .addVertex(Point(721.0, 94.0))
            .addVertex(Point(713.0, 117.0))
            .addVertex(Point(736.0, 144.0))
            .addVertex(Point(720.0, 194.0))
            .addVertex(Point(688.0, 171.0))
            .addVertex(Point(684.0, 144.0))
            .addVertex(Point(698.0, 117.0))
            .addVertex(Point(679.0, 77.0))
            .addVertex(Point(646.0, 85.0))
            .build("woman", "Woman"),
        Polygon.Builder()
            .addVertex(Point(434.0, 85.0))
            .addVertex(Point(592.0, 87.0))
            .addVertex(Point(606.0, 5.0))
            .addVertex(Point(421.0, 5.0))
            .build("frame", "Frame"),
        Polygon.Builder()
            .addVertex(Point(82.0, 463.0))
            .addVertex(Point(95.0, 377.0))
            .addVertex(Point(454.0, 436.0))
            .addVertex(Point(456.0, 476.0))
            .build("dog", "Dog")
    )

    private fun getPolygonData2() = listOf(
        Polygon.Builder()
            .addVertex(Point(553.0, 506.0))
            .addVertex(Point(555.0, 323.0))
            .addVertex(Point(616.0, 47.0))
            .addVertex(Point(658.0, 1.0))
            .addVertex(Point(851.0, 5.0))
            .addVertex(Point(891.0, 83.0))
            .addVertex(Point(899.0, 159.0))
            .addVertex(Point(856.0, 458.0))
            .addVertex(Point(824.0, 506.0))
            .build("bigben", "Big Ben")
    )

    private fun getTextData1() = listOf(
        PageText("peter", "Peter", "Peter"),
        PageText("pan", "Pan", "Pan"),
        PageText("is", "is", "is"),
        PageText("a", "a", "a"),
        PageText("work", "work", "work"),
        PageText("by", "by", "by"),
        PageText("j", "J.", "J"),
        PageText("m", "M.", "M"),
        PageText("barrie", "Barrie,", "Barrie"),
        PageText("in", "in", "in"),
        PageText("the", "the", "the"),
        PageText("form", "form", "form"),
        PageText("of", "of", "of"),
        PageText("a", "a", "a"),
        PageText("number_1904", "1904", "1904"),
        PageText("play", "play", "play"),
        PageText("and", "and", "and"),
        PageText("a", "a", "a"),
        PageText("number_1911", "1911", "1911"),
        PageText("novel", "novel.", "novel")
    )

    private fun getTextData2() = listOf(
        PageText("peter", "Peter", "Peter"),
        PageText("pan", "Pan", "Pan"),
        PageText("and", "and", "and"),
        PageText("his", "his", "his"),
        PageText("posse", "posse", "posse"),
        PageText("are", "are", "are"),
        PageText("flying", "flying", "flying"),
        PageText("over", "over", "over"),
        PageText("the", "the", "the"),
        PageText("big", "Big", "Big"),
        PageText("ben", "Ben,", "Ben"),
        PageText("its", "it's", "it's"),
        PageText("a", "a", "a"),
        PageText("shame", "shame", "shame"),
        PageText("i", "I", "I"),
        PageText("cant", "can't", "can't"),
        PageText("fly", "fly.", "fly")
    )

    private fun getTextDataString1() =
        "Peter Pan is a work by J. M. Barrie, in the form of a 1904 play and a 1911 novel."

    private fun getTextDataString2() =
        "Peter Pan and his posse are flying over the Big Ben, it's a shame I can't fly."
}