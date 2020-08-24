package com.hkurbardovic.interactivereading.handlers.commands

import android.view.View
import com.hkurbardovic.interactivereading.managers.MediaManager

class StopCommand(private val mediaManager: MediaManager) : Command {

    override fun execute(view: View?) {
        mediaManager.stop()
    }
}