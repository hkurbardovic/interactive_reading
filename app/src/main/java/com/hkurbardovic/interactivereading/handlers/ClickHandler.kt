package com.hkurbardovic.interactivereading.handlers

import android.view.View
import com.hkurbardovic.interactivereading.handlers.commands.Command

class ClickHandler(private val commands: List<Command>) : View.OnClickListener {

    override fun onClick(view: View?) {
        commands.forEach {
            it.execute(view)
        }
    }
}