package com.tomweasley.overgrilled.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import com.tomweasley.overgrilled.R
import kotlin.random.Random

class SoundManager(context: Context) {
    private val appContext = context.applicationContext
    private var bgmPlayer: MediaPlayer? = null

    // This controls ONLY the background music
    var isMusicEnabled = true
        set(value) {
            field = value
            if (!value) {
                if (bgmPlayer?.isPlaying == true) bgmPlayer?.pause()
            } else {
                // Resume if we have a player ready
                if (bgmPlayer?.isPlaying == false) bgmPlayer?.start()
            }
        }

    // This controls ONLY the sound effects
    var isSfxEnabled = true

    private val soundPool = SoundPool.Builder().setMaxStreams(5).build()
    private val interactId = soundPool.load(appContext, R.raw.interact, 1)

    fun playMainMenuBGM() = playBGM(R.raw.mainmenu)
    fun playResultBGM() = playBGM(R.raw.resultscreen)

    fun playGameTrackBGM() {
        val track = if (Random.nextBoolean()) R.raw.gametrack1 else R.raw.gametrack2
        playBGM(track)
    }

    private fun playBGM(resId: Int) {
        bgmPlayer?.let {
            try {
                if (it.isPlaying) it.stop()
            } catch (e: Exception) { }
            it.release()
        }

        bgmPlayer = MediaPlayer.create(appContext, resId)?.apply {
            isLooping = true
            // FIX: Changed from isSoundEnabled to isMusicEnabled
            if (isMusicEnabled) start()
        }
    }

    fun playInteract() {
        // FIX: Changed from isSoundEnabled to isSfxEnabled
        if (isSfxEnabled) {
            soundPool.play(interactId, 1f, 1f, 0, 0, 1f)
        }
    }

    fun release() {
        bgmPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        bgmPlayer = null
        soundPool.release()
    }
}