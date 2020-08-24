package com.hkurbardovic.interactivereading.managers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import javax.inject.Inject

class MediaManager @Inject constructor(private val context: Context) {

    interface OnCompletionListener {
        fun onCompleted()
    }

    private var mediaPlayer: MediaPlayer? = null

    private var pausedPosition = 0

    fun play(
        uri: Uri,
        isContinue: Boolean = false,
        onCompletionListener: OnCompletionListener? = null
    ) {
        try {
            val mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(context, uri)
                prepare()
                start()
                if (isContinue) seekTo(pausedPosition)
            }
//            mediaPlayer.setOnPreparedListener {
//                it.start()
//
//            }

            onCompletionListener?.let { it ->
                mediaPlayer.setOnCompletionListener { _ ->
                    pausedPosition = 0
                    it.onCompleted()
                }
            }

            this.mediaPlayer = mediaPlayer
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
        pausedPosition = mediaPlayer?.currentPosition ?: 0
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun isPlaying() = mediaPlayer?.isPlaying ?: false
}