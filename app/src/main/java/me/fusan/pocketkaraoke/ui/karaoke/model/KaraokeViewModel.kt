package me.fusan.pocketkaraoke.ui.karaoke.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokePresenter

class KaraokeViewModel (karaokePresenter_: KaraokePresenter) {
    private var karaokePresenter: KaraokePresenter = karaokePresenter_
    var song: Array<String> = arrayOf("Aj Raate Kono Rupkotha Nei", "Numb")
    var artist: Array<String> = arrayOf("Old School", "Linkin Park")
    var img: Array<Int> = arrayOf<Int>(R.raw.song1, R.raw.song2)
}