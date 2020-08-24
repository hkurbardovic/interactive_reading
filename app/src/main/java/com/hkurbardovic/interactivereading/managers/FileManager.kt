package com.hkurbardovic.interactivereading.managers

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import javax.inject.Inject

class FileManager @Inject constructor() {

    fun readImage(context: Context, child: String): Bitmap? {
        val directory = ContextWrapper(context).getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
        val file = File(directory, child)

        return BitmapFactory.decodeFile(file.path)
    }

    fun getFile(context: Context, child: String): File {
        val directory = ContextWrapper(context).getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
        return File(directory, child)
    }

    companion object {
        private const val DIRECTORY_NAME = "images"
    }
}