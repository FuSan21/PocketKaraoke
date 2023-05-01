package me.fusan.pocketkaraoke.ui.effects

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.effects.presenter.EffectsViewPresenter

class EffectsFragment : Fragment() {
    private lateinit var effectsViewPresenter: EffectsViewPresenter
    lateinit var tv: TextView
    private lateinit var left: Button
    private lateinit var right: Button
    private lateinit var play: Button
    private lateinit var record: Button
    private lateinit var addEffects: Button
    private lateinit var allButtons: Array<Button>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_effects, container, false)
        val activity: AppCompatActivity = activity as AppCompatActivity
        effectsViewPresenter = EffectsViewPresenter(this)


        setHasOptionsMenu(true)

        tv = v.findViewById<View>(R.id.textView) as TextView
        effectsViewPresenter.loadRecordings()

        left = v.findViewById<View>(R.id.btnLeft) as Button
        right = v.findViewById<View>(R.id.btnRight) as Button
        record = v.findViewById<View>(R.id.btnRecord) as Button
        play = v.findViewById<View>(R.id.btnPlay) as Button
        addEffects = v.findViewById<View>(R.id.btnEffects) as Button

        allButtons = arrayOf(left, right, play, record, addEffects)

        left.setOnClickListener {
            effectsViewPresenter.showPreviousRecording()
        }
        right.setOnClickListener {
            effectsViewPresenter.showNextRecording()
        }
        record.setOnClickListener{
            if (record.text == "Record") {
                effectsViewPresenter.startRecording()
                switchButtonState(record, false)
            } else {
                effectsViewPresenter.stopRecording()
                switchButtonState(record, true)

            }
        }
        play.setOnClickListener{
            if (play.text == "Play"){
                effectsViewPresenter.playCurrentRecording()
                switchButtonState(play, false)
            } else {
                effectsViewPresenter.stopPlayback()
                switchButtonState(play, true)
            }
        }
        addEffects.setOnClickListener{
            val intent = Intent(context, EffectsActivity::class.java)
            intent.putExtra("name", tv.text.toString())
            startActivity(intent)
        }

        return v
    }

    private fun switchButtonState(btnClicked: Button, state: Boolean) {
        lateinit var startText: String
        val endText = getString(R.string.stop)
        when (btnClicked) {
            play -> startText = getString(R.string.play)
            record -> startText = getString(R.string.record)
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

    fun switchButtonStateForPlayback(state: Boolean) {
        switchButtonState(play, state)
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            effectsViewPresenter.loadRecordings()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        effectsViewPresenter.releaseResources()
    }
}