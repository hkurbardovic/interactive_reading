package com.hkurbardovic.interactivereading.custom

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class WordClickableSpannable(
    private val id: String,
    private val onClickListener: OnClickListener?,
    private val color: Int? = null,
    private val isUnderline: Boolean = true,
    private val isBold: Boolean = false
) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        if (isUnderline) ds.isUnderlineText = true
        if (isBold) ds.typeface = Typeface.DEFAULT_BOLD
        color?.let {
            ds.color = it
        }
    }

    override fun onClick(view: View) {
        onClickListener?.onWordClick(id)
    }

    interface OnClickListener {
        fun onWordClick(id: String)
    }
}