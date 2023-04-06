package me.fusan.pocketkaraoke.ui.effects.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EffectsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is effects Fragment"
    }
    val text: LiveData<String> = _text
}