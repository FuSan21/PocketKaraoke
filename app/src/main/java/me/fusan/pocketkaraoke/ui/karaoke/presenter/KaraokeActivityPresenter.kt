package me.fusan.pocketkaraoke.ui.karaoke.presenter

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.media.MediaRecorder
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.KaraokeActivity
import me.fusan.pocketkaraoke.ui.karaoke.model.KaraokeActivityModel
import java.io.IOException
import java.io.InputStream
import java.text.DecimalFormat

class KaraokeActivityPresenter (karaokeActivity_: KaraokeActivity) {
    var karaokeActivityModel: KaraokeActivityModel = KaraokeActivityModel(this)
    private var karaokeActivity: KaraokeActivity = karaokeActivity_

    fun setCurrentSong(songID: Int) {
        karaokeActivityModel.currentSong = songID
    }

    fun karaokeSwitch(context: Context) {
        if (karaokeActivityModel.isRecording) {
            stopKaraoke()
            calculateTotalKaraokeScore()
        } else startKaraoke(context)
    }

    private fun startKaraoke(context: Context) {
        karaokeActivity.btnRecord.setText(R.string.stop)
        karaokeActivityModel.isRecording = true
        karaokeActivityModel.recorder = MediaRecorder()
        karaokeActivityModel.recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("${karaokeActivityModel.recordPath}${karaokeActivityModel.fileName}.mp3")
            try {
                prepare()
            } catch (_: IOException) {}

            start()
        }
        karaokeActivity.timer.start()
        karaokeActivityModel.maxSpikes = (karaokeActivity.karaokeWaveform.measuredWidth / (karaokeActivityModel.width + karaokeActivityModel.distance)).toInt()
        lyricsLoading(karaokeActivityModel.currentSong, context)
    }

    private fun stopKaraoke() {
        karaokeActivityModel.isRecording = false
        karaokeActivity.btnRecord.setText(R.string.start)
        karaokeActivity.timer.stop()
        karaokeActivityModel.recorder.apply {
            stop()
            reset()
            release()
        }
    }

    private fun drawWaveform(canvasWidth: Int, canvasHeight: Int) {
        val bmp = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.color = Color.RED
        val canvas = Canvas(bmp)

        karaokeActivityModel.spikes.forEach {
            canvas.drawRoundRect(it, karaokeActivityModel.radius, karaokeActivityModel.radius, paint)
        }
        karaokeActivity.drawCanvas(bmp)
    }

    fun addAmplitude() {
        val amp = karaokeActivityModel.recorder.maxAmplitude.toFloat()
        val norm = (amp.toInt() / 15).coerceAtMost(karaokeActivity.karaokeWaveform.measuredHeight).toFloat()
        karaokeActivityModel.amplitudes.add(norm)

        karaokeActivityModel.spikes.clear()
        val amps = karaokeActivityModel.amplitudes.takeLast(karaokeActivityModel.maxSpikes)

        for(i in amps.indices) {
            val left = karaokeActivity.karaokeWaveform.measuredWidth - i*(karaokeActivityModel.width+karaokeActivityModel.distance)
            val top = karaokeActivity.karaokeWaveform.measuredHeight/2 - amps[i]/2
            val right = left + karaokeActivityModel.width
            val bottom = top + amps[i]
            karaokeActivityModel.spikes.add(RectF(left, top, right, bottom))
        }
        drawWaveform(karaokeActivity.karaokeWaveform.measuredWidth, karaokeActivity.karaokeWaveform.measuredHeight)
    }

    private fun lyricsLoading(songNo: Int, context: Context) {
        val res: Resources = context.resources
        var inputStream: InputStream = res.openRawResource(R.raw.song1_lyrics)
        when (songNo) {
            1 -> inputStream= res.openRawResource(R.raw.song1_lyrics)
            2 -> inputStream= res.openRawResource(R.raw.song2_lyrics)
            3 -> inputStream= res.openRawResource(R.raw.song1_lyrics)
        }
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }
        lineList.forEach{
            if(it.contains(Regex("""^\[[length]+:"""))) {
                karaokeActivityModel.songEndTime = it.substring(8,16).replace('.', ':')
                val min = karaokeActivityModel.songEndTime.substring(0,2).toInt()
                val sec = karaokeActivityModel.songEndTime.substring(3,5).toInt()
                val mili = karaokeActivityModel.songEndTime.substring(6,7).toInt()
                karaokeActivityModel.songTotalSegments = min*600 + sec*10 + mili
            }
            else if(!it.contains(Regex("""^\[[a-z]+:"""))) {
                karaokeActivityModel.lyricStartTime.add(it.substringBefore(']').trimStart('[').replace('.', ':'))
                karaokeActivityModel.lyrics.add(it.substringAfter(']'))
            }
        }
    }

    fun lyricsAndScoreHandler(duration: String) {
        if (duration == karaokeActivityModel.songEndTime) {
            stopKaraoke()
            calculateTotalKaraokeScore()
        } else {
            calculateCurrentKaraokeScore()
            val currentLyricsPosition = karaokeActivityModel.lyricStartTime.indexOf(duration)
            if (currentLyricsPosition != -1) {
                drawLyrics(karaokeActivityModel.lyrics[currentLyricsPosition], karaokeActivity.lyricsContainer.measuredWidth, karaokeActivity.lyricsContainer.measuredHeight)
            }
        }

    }

    private fun calculateCurrentKaraokeScore() {
        karaokeActivityModel.scores.add(((50..100).random()).toFloat())
    }

    private fun calculateTotalKaraokeScore() {
        karaokeActivityModel.karaokeScore = karaokeActivityModel.scores.sum()/karaokeActivityModel.songTotalSegments
        val df = DecimalFormat("#.##")
        karaokeActivity.showKaraokeScore(df.format(karaokeActivityModel.karaokeScore))
    }

    private fun drawLyrics(lyric: String, canvasWidth: Int, canvasHeight: Int) {
        val bmp = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.isLinearText = true
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.strokeWidth = 0.3f
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 100f
        val canvas = Canvas(bmp)
        if (lyric.length < 20) canvas.drawText(lyric, 500f, (canvasHeight/2).toFloat(), paint) else {
            val sub1: String
            val sub2: String
            val mid = dividedLineIndex(lyric)

            sub1 = lyric.substring(0, mid)
            sub2 = lyric.substring(mid+1, lyric.length)
            canvas.drawText(sub1, 500f, (canvasHeight/2).toFloat() - 100f, paint)
            canvas.drawText(sub2, 500f, (canvasHeight/2).toFloat() + 100f, paint)
        }
        karaokeActivity.drawLyrics(bmp)
    }

    private fun dividedLineIndex(lyric: String): Int {
        val mid = lyric.length / 2
        val sub1Index = lyric.substring(0, mid).lastIndexOf(' ')
        val sub2Index = mid + lyric.substring(mid, lyric.length).indexOf(' ')
        println("$mid >> $sub1Index >> $sub2Index")
        return if((mid-sub1Index)<(sub2Index-mid)) sub1Index else sub2Index
    }

    fun setPlayTime(duration: String) {
        karaokeActivityModel.playTime = duration
    }

}