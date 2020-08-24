package com.hkurbardovic.interactivereading.handlers.commands

import android.view.View

interface Command {

    fun execute(view: View? = null)
}