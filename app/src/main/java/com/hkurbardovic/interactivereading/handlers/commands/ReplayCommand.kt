package com.hkurbardovic.interactivereading.handlers.commands

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import android.widget.ImageView

class ReplayCommand(private val stopCommand: StopCommand, private val playCommand: PlayCommand) :
    Command {

    override fun execute(view: View?) {
        stopCommand.execute()
        playCommand.execute()

        if (view == null) return

        if (view is ImageView) {
            val drawable = view.drawable

            if (drawable is AnimatedVectorDrawable) {
                drawable.start()
            }
        }
    }
}