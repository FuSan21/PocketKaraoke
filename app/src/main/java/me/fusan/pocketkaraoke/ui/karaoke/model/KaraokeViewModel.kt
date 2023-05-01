package me.fusan.pocketkaraoke.ui.karaoke.model

import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokeViewPresenter

class KaraokeViewModel (karaokeViewPresenter_: KaraokeViewPresenter) {
    private var karaokeViewPresenter: KaraokeViewPresenter = karaokeViewPresenter_
    var song: Array<String> = arrayOf("Aj Raate Kono Rupkotha Nei", "Numb")
    var artist: Array<String> = arrayOf("Old School", "Linkin Park")
    var img: Array<Int> = arrayOf<Int>(R.raw.song1, R.raw.song2)
    val RECORDING_PERMISSION_CODE = 200
    val TAG = "PERMISSION_TAG"
}