package com.hkurbardovic.interactivereading.handlers.commands

import android.view.View
import com.hkurbardovic.interactivereading.managers.MediaManager

class PlayPauseCommand(
    private val mediaManager: MediaManager,
    private val playCommand: PlayCommand,
    private val pauseCommand: PauseCommand
) : Command {

    override fun execute(view: View?) {
        if (mediaManager.isPlaying()) pauseCommand.execute() else playCommand.execute()
    }
}