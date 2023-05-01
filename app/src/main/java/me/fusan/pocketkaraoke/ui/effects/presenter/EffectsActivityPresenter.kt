package me.fusan.pocketkaraoke.ui.effects.presenter

import android.media.MediaPlayer
import android.media.audiofx.EnvironmentalReverb
import android.os.CountDownTimer
import me.fusan.pocketkaraoke.ui.effects.EffectsActivity
import me.fusan.pocketkaraoke.ui.effects.model.EffectsActivityModel
import java.io.IOException


class EffectsActivityPresenter(effectsActivity_: EffectsActivity) {
    private var effectsActivityModel: EffectsActivityModel = EffectsActivityModel(this)
    private var effectsActivity: EffectsActivity = effectsActivity_

    private fun prepareForPlayingRecording() {
        effectsActivityModel.mediaPlayer = MediaPlayer()
        val ext1 = effectsActivityModel.recordPath + effectsActivity.tv.text
        try {
            effectsActivityModel.mediaPlayer.setDataSource(ext1)
            effectsActivityModel.mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun resetMediaPlayer() {
        try {
            effectsActivityModel.mediaPlayer.reset()
            effectsActivityModel.mediaPlayer.release()
        } catch (e: IllegalStateException) {
            // media player is not initialized
        }
    }

    fun loadMediaEndTime() {
        prepareForPlayingRecording()
        effectsActivityModel.customizedPlaybackTimeEnd = effectsActivityModel.mediaPlayer.duration
        resetMediaPlayer()
    }

    fun returnFormattedMediaEndTime(): Float {
        return effectsActivityModel.customizedPlaybackTimeEnd.toFloat()/1000
    }

    fun returnFormattedMediaStartTime(): Float {
        return effectsActivityModel.customizedPlaybackTimeStart.toFloat()/1000
    }

    fun playRecording(btnClicked:Int, pitchEffect: Boolean, reverbEffect: Boolean, customPlaybackTimeEffect: Boolean) {
        prepareForPlayingRecording()
        if (reverbEffect) {
            addReverbEffect()
        }
        if (pitchEffect) {
            addPitchEffect()
        }
        effectsActivityModel.mediaPlayer.start()
        effectsActivityModel.mediaPlayer.setOnCompletionListener {
            when(btnClicked) {
                0 -> effectsActivity.switchButtonStateForPlayOriginal(true)
                1 -> effectsActivity.switchButtonStateForPlay1(true)
                2 -> effectsActivity.switchButtonStateForPlay2(true)
                3 -> effectsActivity.switchButtonStateForPlay3(true)
                4 -> effectsActivity.switchButtonStateForPlayModified(true)
            }
            resetMediaPlayer()
        }
        if (customPlaybackTimeEffect) {
            addCustomPlaybackTimeEffect(btnClicked != 4)
        }
    }

    private fun addCustomPlaybackTimeEffect(soloEffect: Boolean) {
        effectsActivityModel.mediaPlayer.seekTo(effectsActivityModel.customizedPlaybackTimeStart)
        val customizedPlayTime = effectsActivityModel.customizedPlaybackTimeEnd - effectsActivityModel.customizedPlaybackTimeStart
        object : CountDownTimer(customizedPlayTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if(soloEffect) effectsActivity.switchButtonStateForPlay3(true)
                else effectsActivity.switchButtonStateForPlayModified(true)
                stopPlayback()
                resetMediaPlayer()
            }
        }.start()
    }

    private fun addPitchEffect() {
        effectsActivityModel.mediaPlayer.playbackParams = effectsActivityModel.mediaPlayer.playbackParams.setPitch(1.2f)
    }

    private fun addReverbEffect() {
        val eReverb = EnvironmentalReverb(0, 0)
        effectsActivityModel.mediaPlayer.attachAuxEffect(eReverb.id)
    }

    fun stopPlayback() {
        try {
            effectsActivityModel.mediaPlayer.stop()
            resetMediaPlayer()
        } catch (e: IllegalStateException) {
            // media player is not initialized
        }
    }

    fun releaseResources() {
        try {
            effectsActivityModel.mediaPlayer.release()
        } catch (e: IllegalStateException) {
            // media player is not initialized
        }
    }

    fun pitchEffectSwitch() {
        effectsActivity.switchActivatedPitchEffect(effectsActivityModel.isPitchEffectActive)
        effectsActivityModel.isPitchEffectActive = !effectsActivityModel.isPitchEffectActive
    }

    fun reverbEffectSwitch() {
        effectsActivity.switchActivatedReverbEffect(effectsActivityModel.isReverbEffectActive)
        effectsActivityModel.isReverbEffectActive = !effectsActivityModel.isReverbEffectActive
    }

    fun customPlaybackTimeEffectSwitch() {
        effectsActivity.switchActivatedCustomPlaybackTimeEffect(effectsActivityModel.isPlaybackTimeCustomizedActive)
        effectsActivityModel.isPlaybackTimeCustomizedActive = !effectsActivityModel.isPlaybackTimeCustomizedActive
    }

    fun updatePlaybackTime(values: List<Float>) {
        effectsActivityModel.customizedPlaybackTimeStart = (values[0]*1000).toInt()
        effectsActivityModel.customizedPlaybackTimeEnd = (values[1]*1000).toInt()
    }
}