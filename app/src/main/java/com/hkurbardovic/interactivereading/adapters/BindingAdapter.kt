package com.hkurbardovic.interactivereading.adapters

import android.graphics.drawable.AnimatedVectorDrawable
import android.text.SpannableStringBuilder
import android.text.method.MovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hkurbardovic.interactivereading.R

@BindingAdapter(value = ["bindMovementMethod", "bindSpannableString"])
fun bindMovementMethod(
    view: TextView,
    movementMethod: MovementMethod?,
    spannableString: SpannableStringBuilder?
) {
    movementMethod?.apply {
        view.movementMethod = this
    }
    spannableString?.apply {
        view.text = this
    }
}

@BindingAdapter("isPlaying")
fun bindMediaManagerIsPlaying(
    view: ImageView,
    isPlaying: Boolean
) {
    val context = view.context
    val drawable =
        if (isPlaying) context.getDrawable(R.drawable.pause_to_play) else context.getDrawable(R.drawable.play_to_pause)
    view.setImageDrawable(drawable)

    if (drawable is AnimatedVectorDrawable) {
        drawable.start()
    }
}