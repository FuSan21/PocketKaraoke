package me.fusan.pocketkaraoke.ui.karaoke.model

import android.annotation.SuppressLint
import android.graphics.RectF
import android.media.MediaRecorder
import android.os.Environment
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokeActivityPresenter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class KaraokeActivityModel (karaokeActivityPresenter_: KaraokeActivityPresenter) {
    private var karaokeActivityPresenter: KaraokeActivityPresenter = karaokeActivityPresenter_
    lateinit var recorder: MediaRecorder
    var appPath = "${Environment.getExternalStorageDirectory()}/PocketKaraoke/"
    var recordPath = appPath+"YourRecordings/"
    @SuppressLint("SimpleDateFormat")
    private var simpleDateFormat = SimpleDateFormat("yyyy.MM.dd_hh.mm.ss")
    private var date: String = simpleDateFormat.format(Date())
    var fileName = "karaoke_recording_$date"
    var currentSong: Int = 0
    lateinit var playTime: String
    var isRecording = false
    var amplitudes = ArrayList<Float>()
    var scores = mutableListOf<Float>()
    var spikes = ArrayList<RectF>()
    var radius = 6f
    var width = 9f
    var distance = 6f
    var maxSpikes = 0
    var lyricStartTime = mutableListOf<String>()
    var songEndTime = "00:00:00"
    var songTotalSegments = 0
    var lyrics = mutableListOf<String>()
    var karaokeScore = 0F
}