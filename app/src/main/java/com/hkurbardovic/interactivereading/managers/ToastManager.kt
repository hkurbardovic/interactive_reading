package com.hkurbardovic.interactivereading.managers

import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.hkurbardovic.interactivereading.utilities.AnimationUtils
import kotlinx.coroutines.*
import javax.inject.Inject

class ToastManager @Inject constructor() {

    private var job: Job? = null

    fun showToast(
        x: Int?,
        y: Int?,
        string: String?,
        textView: TextView?,
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ) {
        if (x == null || y == null || string == null || textView == null) return

        textView.text = string

        val lp = textView.layoutParams as FrameLayout.LayoutParams
        lp.marginStart = x - (textView.width / 2)
        lp.topMargin = y - (textView.height / 2)
        textView.layoutParams = lp

        AnimationUtils.animateVisibility(textView, true)

        job = lifecycleCoroutineScope.launch {
            job?.cancel()

            AnimationUtils.animateVisibility(textView, true)
            withContext(Dispatchers.IO) {
                delay(ANIMATION_DELAY)
                withContext(Dispatchers.Main) {
                    AnimationUtils.animateVisibility(textView, false)
                }
            }
        }
    }

    companion object {
        private const val ANIMATION_DELAY = 1000L
    }
}