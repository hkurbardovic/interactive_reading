package com.hkurbardovic.interactivereading.handlers.commands

import android.view.View
import com.hkurbardovic.interactivereading.managers.MediaManager

class PauseCommand(
    private val mediaManager: MediaManager,
    private val func: () -> Unit
) : Command {

    override fun execute(view: View?) {
        mediaManager.pause()
        func()
    }
}