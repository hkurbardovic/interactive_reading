package com.hkurbardovic.interactivereading.utilities

import android.view.View

object AnimationUtils {

    private const val ALPHA_INVISIBLE = 0f
    private const val ALPHA_VISIBLE = 1f
    private const val ANIMATION_DURATION = 1000L

    fun animateVisibility(contentView: View, isVisible: Boolean) {
        if (isVisible) {
            contentView.apply {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = ALPHA_INVISIBLE
                visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()
                    .alpha(ALPHA_VISIBLE)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(null)
            }
        } else {
            contentView.apply {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = ALPHA_VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()
                    .alpha(ALPHA_INVISIBLE)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(null)
            }
        }
    }
}