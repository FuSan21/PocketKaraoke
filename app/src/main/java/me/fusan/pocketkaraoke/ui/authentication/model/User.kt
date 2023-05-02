package me.fusan.pocketkaraoke.ui.authentication.model

class User(val email: String) {
    companion object {
        data class LastKaraokeResult(var songID: Int, var score: String)
    }
}