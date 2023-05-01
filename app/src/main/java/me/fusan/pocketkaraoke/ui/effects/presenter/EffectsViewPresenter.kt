package me.fusan.pocketkaraoke.ui.effects.presenter

import android.media.MediaPlayer
import android.media.MediaRecorder
import me.fusan.pocketkaraoke.ui.effects.EffectsFragment
import me.fusan.pocketkaraoke.ui.effects.model.EffectsViewModel
import java.io.File
import java.io.IOException
import java.lang.Exception

class EffectsViewPresenter(effectsFragment_: EffectsFragment) {
    var effectsViewModel: EffectsViewModel = EffectsViewModel(this)
    private var effectsFragment: EffectsFragment = effectsFragment_

    fun loadRecordings() {
        effectsViewModel.fileList = effectsViewModel.filePath.listFiles() as Array<File>
        effectsViewModel.fileList.reverse()
        effectsViewModel.pos = -1

        if (effectsViewModel.fileList.isNotEmpty()) {
            effectsViewModel.pos = 0
            val name: String = effectsViewModel.fileList[0].name.toString()
            effectsFragment.tv.text = name
        }
    }

    fun showPreviousRecording() {
        if (effectsViewModel.pos > 0) effectsViewModel.pos-- else if (effectsViewModel.pos == 0) effectsViewModel.pos = effectsViewModel.fileList.size - 1
        val name = effectsViewModel.fileList[effectsViewModel.pos].name.toString()
        effectsFragment.tv.text = name
    }

    fun showNextRecording() {
        if (effectsViewModel.pos < effectsViewModel.fileList.size - 1) effectsViewModel.pos++ else if (effectsViewModel.pos == effectsViewModel.fileList.size - 1) effectsViewModel.pos = 0
        val name = effectsViewModel.fileList[effectsViewModel.pos].name.toString()
        effectsFragment.tv.text = name
    }

    fun startRecording() {
        effectsViewModel.isRecording = true
        effectsViewModel.recorder = MediaRecorder()
        effectsViewModel.recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("${effectsViewModel.recordPath}${effectsViewModel.fileName}.mp3")
            try {
                prepare()
            } catch (_: IOException) {}
            start()
        }
    }

    fun stopRecording() {
        effectsViewModel.isRecording = false
        effectsViewModel.recorder.apply {
            stop()
            reset()
            release()
        }
        loadRecordings()
    }

    fun playCurrentRecording() {
        effectsViewModel.mediaPlayer = MediaPlayer()
        val ext1 = effectsViewModel.recordPath + effectsFragment.tv.text
        try {
            effectsViewModel.mediaPlayer.setDataSource(ext1)
            effectsViewModel.mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        effectsViewModel.mediaPlayer.start()
        effectsViewModel.mediaPlayer.setOnCompletionListener {
            effectsFragment.switchButtonStateForPlayback(true)
            effectsViewModel.mediaPlayer.reset()
            effectsViewModel.mediaPlayer.release()
        }
    }

    fun stopPlayback() {
        effectsViewModel.mediaPlayer.stop()
        effectsViewModel.mediaPlayer.reset()
        effectsViewModel.mediaPlayer.release()
    }

    fun releaseResources() {
        try {effectsViewModel.mediaPlayer.release()}
        catch (_:Exception) {}
    }
}