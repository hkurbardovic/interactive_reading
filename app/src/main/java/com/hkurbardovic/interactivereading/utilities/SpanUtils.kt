package com.hkurbardovic.interactivereading.utilities

import android.text.SpannableStringBuilder
import android.text.Spanned
import com.hkurbardovic.interactivereading.custom.WordClickableSpannable

object SpanUtils {

    fun getSpannableText(
        wordClickListener: WordClickableSpannable.OnClickListener,
        position: Int
    ): SpannableStringBuilder? {
        val textDataString = PageDataUtils.getTextDataString(position) ?: return null
        val spannableString = SpannableStringBuilder(textDataString)
        val data = PageDataUtils.getTextData(position) ?: return null

        var startIndex = 0
        data.forEach {
            spannableString.setSpan(
                WordClickableSpannable(
                    it.id,
                    wordClickListener,
                    null,
                    true
                ),
                startIndex,
                startIndex + it.title.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            startIndex += it.title.length + 1
        }

        return spannableString
    }
}