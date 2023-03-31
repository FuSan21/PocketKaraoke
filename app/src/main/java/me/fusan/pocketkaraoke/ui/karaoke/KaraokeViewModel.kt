package me.fusan.pocketkaraoke.ui.karaoke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KaraokeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is karaoke Fragment"
    }
    val text: LiveData<String> = _text
}