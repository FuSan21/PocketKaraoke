package me.fusan.pocketkaraoke.ui.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChallengesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is challenges Fragment"
    }
    val text: LiveData<String> = _text
}