package me.fusan.pocketkaraoke.ui.effects.model

import android.media.MediaPlayer
import android.os.Environment
import me.fusan.pocketkaraoke.ui.effects.presenter.EffectsActivityPresenter

class EffectsActivityModel(effectsActivityPresenter_: EffectsActivityPresenter) {
    private var effectsActivityPresenter: EffectsActivityPresenter = effectsActivityPresenter_
    lateinit var mediaPlayer: MediaPlayer
    var appPath = "${Environment.getExternalStorageDirectory()}/PocketKaraoke/"
    var recordPath = appPath+"YourRecordings/"
    var isPitchEffectActive = false
    var isReverbEffectActive = false
    var isPlaybackTimeCustomizedActive = false
    var customizedPlaybackTimeStart = 0
    var customizedPlaybackTimeEnd = 0
}