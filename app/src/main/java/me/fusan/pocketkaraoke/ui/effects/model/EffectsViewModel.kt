package me.fusan.pocketkaraoke.ui.effects.model

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import me.fusan.pocketkaraoke.ui.effects.presenter.EffectsViewPresenter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class EffectsViewModel(effectsViewPresenter_: EffectsViewPresenter) {
    private var effectsViewPresenter: EffectsViewPresenter = effectsViewPresenter_
    lateinit var fileList: Array<File>
    var pos = 0
    var isRecording = false
    lateinit var recorder: MediaRecorder
    var appPath = "${Environment.getExternalStorageDirectory()}/PocketKaraoke/"
    var recordPath = appPath+"YourRecordings/"
    val filePath = File(recordPath)
    @SuppressLint("SimpleDateFormat")
    private var simpleDateFormat = SimpleDateFormat("yyyy.MM.dd_hh.mm.ss")
    private var date: String = simpleDateFormat.format(Date())
    var fileName = "custom_recording_$date"
    lateinit var mediaPlayer: MediaPlayer
}