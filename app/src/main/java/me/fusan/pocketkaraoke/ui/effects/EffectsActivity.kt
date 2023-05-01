package me.fusan.pocketkaraoke.ui.effects

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.effects.presenter.EffectsActivityPresenter
import java.math.RoundingMode
import java.text.DecimalFormat

class EffectsActivity : AppCompatActivity() {
    private lateinit var effectsActivityPresenter: EffectsActivityPresenter
    lateinit var tv: TextView
    private lateinit var btnPlayOriginal: Button
    private lateinit var btnChangePitch: Button
    private lateinit var btnPlay1: Button
    private lateinit var btnAddReverb: Button
    private lateinit var btnPlay2: Button
    private lateinit var btnChangePlaybackTime: Button
    private lateinit var btnPlay3: Button
    private lateinit var btnPlayModified: Button
    private lateinit var btnSaveModified: Button
    private lateinit var playbackTimeSlider: RangeSlider
    private lateinit var allButtons: Array<Button>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effects)

        effectsActivityPresenter = EffectsActivityPresenter(this)

        tv = findViewById<View>(R.id.textView) as TextView
        tv.text = intent.getStringExtra("name")

        btnPlayOriginal = findViewById<View>(R.id.btnPlayOriginal) as Button
        btnChangePitch = findViewById<View>(R.id.btnPitch) as Button
        btnPlay1 = findViewById<View>(R.id.btnPlay1) as Button
        btnAddReverb = findViewById<View>(R.id.btnReverb) as Button
        btnPlay2 = findViewById<View>(R.id.btnPlay2) as Button
        btnChangePlaybackTime = findViewById<View>(R.id.btnPlayback) as Button
        btnPlay3 = findViewById<View>(R.id.btnPlay3) as Button
        btnPlayModified = findViewById<View>(R.id.btnPlayModified) as Button
        btnSaveModified = findViewById<View>(R.id.btnSaveModified) as Button
        playbackTimeSlider = findViewById<View>(R.id.playbackTimeSlider) as RangeSlider
        allButtons = arrayOf(btnPlayOriginal, btnChangePitch, btnPlay1, btnAddReverb, btnPlay2, btnChangePlaybackTime, btnPlay3, btnPlayModified, btnSaveModified)

        initializePlaybackTimeSlider()

        btnPlayOriginal.setOnClickListener {
            if (btnPlayOriginal.text == getString(R.string.play_original)){
                effectsActivityPresenter.playRecording(0,false, false, false)
                switchButtonState(btnPlayOriginal, false)
            } else {
                effectsActivityPresenter.stopPlayback()
                switchButtonState(btnPlayOriginal, true)
            }
        }

        btnChangePitch.setOnClickListener {
            effectsActivityPresenter.pitchEffectSwitch()
        }

        btnPlay1.setOnClickListener {
            if (btnPlay1.text == getString(R.string.play_1)){
                effectsActivityPresenter.playRecording(1, true, false, false)
                switchButtonState(btnPlay1, false)
            } else {
                effectsActivityPresenter.stopPlayback()
                switchButtonState(btnPlay1, true)
            }
        }

        btnAddReverb.setOnClickListener {
            effectsActivityPresenter.reverbEffectSwitch()
        }

        btnPlay2.setOnClickListener {
            if (btnPlay2.text == getString(R.string.play_2)){
                effectsActivityPresenter.playRecording(2, false, true, false)
                switchButtonState(btnPlay2, false)
            } else {
                effectsActivityPresenter.stopPlayback()
                switchButtonState(btnPlay2, true)
            }
        }

        playbackTimeSlider.addOnChangeListener { rangeSlider, _, _ ->
            effectsActivityPresenter.updatePlaybackTime(rangeSlider.values)
        }

        btnChangePlaybackTime.setOnClickListener {
            effectsActivityPresenter.customPlaybackTimeEffectSwitch()
        }

        btnPlay3.setOnClickListener {
            if (btnPlay3.text == getString(R.string.play_3)){
                effectsActivityPresenter.playRecording(3, false, false, true)
                switchButtonState(btnPlay3, false)
            } else {
                effectsActivityPresenter.stopPlayback()
                switchButtonState(btnPlay3, true)
            }
        }

        btnPlayModified.setOnClickListener {
            if (btnPlayModified.text == getString(R.string.play_modified)){
                effectsActivityPresenter.playRecording(4, isEffectActive(btnChangePitch), isEffectActive(btnAddReverb), isEffectActive(btnChangePlaybackTime))
                switchButtonState(btnPlayModified, false)
            } else {
                effectsActivityPresenter.stopPlayback()
                switchButtonState(btnPlayModified, true)
            }
        }

        btnSaveModified.setOnClickListener {
            Toast.makeText(applicationContext, "Saved Custom Recording", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isEffectActive (button: Button): Boolean {
        return button.backgroundTintList == getColorStateList(R.color.effect_active_btn)
    }

    private fun initializePlaybackTimeSlider() {
        effectsActivityPresenter.loadMediaEndTime()
        playbackTimeSlider.valueFrom = 0.0f
        playbackTimeSlider.valueTo = effectsActivityPresenter.returnFormattedMediaEndTime()
        playbackTimeSlider.setValues(0.0f,effectsActivityPresenter.returnFormattedMediaEndTime())
        playbackTimeSlider.setLabelFormatter { value: Float ->
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            if (value<60) {
                df.format(value)+"s"
            } else "${(value/60).toInt()}m ${df.format(value%60)}s"
        }
    }

    private fun switchButtonState(btnClicked: Button, state: Boolean) {
        lateinit var startText: String
        val endText = getString(R.string.stop)
        when (btnClicked) {
            btnPlayOriginal -> startText = getString(R.string.play_original)
            btnPlay1 -> startText = getString(R.string.play_1)
            btnPlay2 -> startText = getString(R.string.play_2)
            btnPlay3 -> startText = getString(R.string.play_3)
            btnPlayModified -> startText = getString(R.string.play_modified)
        }
        allButtons.forEach {
            if (it == btnClicked) {
                if (state) btnClicked.text = startText
                else btnClicked.text = endText
            } else {
                it.isEnabled = state
            }
        }
    }

    fun switchButtonStateForPlayOriginal(state: Boolean) {
        switchButtonState(btnPlayOriginal, state)
    }

    fun switchButtonStateForPlay1(state: Boolean) {
        switchButtonState(btnPlay1, state)
    }

    fun switchButtonStateForPlay2(state: Boolean) {
        switchButtonState(btnPlay2, state)
    }

    fun switchButtonStateForPlay3(state: Boolean) {
        switchButtonState(btnPlay3, state)
    }

    fun switchButtonStateForPlayModified(state: Boolean) {
        switchButtonState(btnPlayModified, state)
    }

    fun switchActivatedPitchEffect(state: Boolean) {
        if (state) btnChangePitch.backgroundTintList = getColorStateList(R.color.default_btn)
        else btnChangePitch.backgroundTintList = getColorStateList(R.color.effect_active_btn)
    }

    fun switchActivatedReverbEffect(state: Boolean) {
        if (state) btnAddReverb.backgroundTintList = getColorStateList(R.color.default_btn)
        else btnAddReverb.backgroundTintList = getColorStateList(R.color.effect_active_btn)
    }

    fun switchActivatedCustomPlaybackTimeEffect(state: Boolean) {
        if (state) btnChangePlaybackTime.backgroundTintList = getColorStateList(R.color.default_btn)
        else btnChangePlaybackTime.backgroundTintList = getColorStateList(R.color.effect_active_btn)
    }

    override fun onDestroy() {
        super.onDestroy()
        effectsActivityPresenter.releaseResources()
    }
}