package me.fusan.pocketkaraoke.ui.karaoke
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokeActivityPresenter


class KaraokeActivity : AppCompatActivity(), Timer.OnTimerTickListener {
    private lateinit var karaokeActivityPresenter: KaraokeActivityPresenter
    lateinit var karaokeWaveform: ImageView;
    lateinit var lyricsContainer: ImageView;
    lateinit var btnRecord: Button;
    lateinit var timerContainer: TextView;
    lateinit var timer: Timer
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karaoke)
        karaokeActivityPresenter = KaraokeActivityPresenter(this)
        context = this
        timer = Timer(this)
        karaokeWaveform = findViewById<ImageView>(R.id.karaokeWaveformContainer)
        lyricsContainer = findViewById<ImageView>(R.id.lyricsContainer)
        btnRecord = findViewById<Button>(R.id.karaokeButton)
        timerContainer = findViewById<TextView>(R.id.timerContainer)
        karaokeActivityPresenter.setCurrentSong(intent.getIntExtra("songID", 0))
        btnRecord.setOnClickListener {
            karaokeActivityPresenter.karaokeSwitch(applicationContext)
        }
    }

    fun drawCanvas(bmp: Bitmap) {
        karaokeWaveform.setImageBitmap(bmp)
    }

    fun drawLyrics(bmp: Bitmap) {
        lyricsContainer.setImageBitmap(bmp)
    }

    fun showKaraokeScore(score: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Congratulations")
        builder.setMessage("Your score is: $score")
        builder.setPositiveButton("Finish") { _, _ ->
            finish()
        }
        builder.show()
    }

    override fun onTimerTick(duration: String) {
        timerContainer.text = duration
        karaokeActivityPresenter.setPlayTime(duration)
        karaokeActivityPresenter.addAmplitude()
        karaokeActivityPresenter.lyricsAndScoreHandler(duration)
    }

}