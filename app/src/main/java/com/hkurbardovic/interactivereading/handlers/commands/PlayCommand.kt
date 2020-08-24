package com.hkurbardovic.interactivereading.handlers.commands

import android.net.Uri
import android.view.View
import com.hkurbardovic.interactivereading.managers.MediaManager

class PlayCommand(
    private val mediaManager: MediaManager,
    private val uri: Uri,
    private val isContinue: Boolean = false,
    private val onCompletionListener: MediaManager.OnCompletionListener,
    private val func: () -> Unit
) : Command {

    override fun execute(view: View?) {
        mediaManager.play(uri, isContinue, onCompletionListener)
        func()
    }
}