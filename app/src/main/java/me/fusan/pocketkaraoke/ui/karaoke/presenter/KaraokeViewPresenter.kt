package me.fusan.pocketkaraoke.ui.karaoke.presenter

import android.content.Context
import me.fusan.pocketkaraoke.ui.karaoke.CustomList
import me.fusan.pocketkaraoke.ui.karaoke.KaraokeFragment
import me.fusan.pocketkaraoke.ui.karaoke.model.KaraokeViewModel

class KaraokeViewPresenter (karaokeFragment_: KaraokeFragment) {

        var karaokeViewModel: KaraokeViewModel = KaraokeViewModel(this)
        private var karaokeFragment: KaraokeFragment = karaokeFragment_

        fun generateCustomListView(v: Context): CustomList {
                return CustomList(v, karaokeViewModel.song, karaokeViewModel.artist, karaokeViewModel.img)
        }

        fun returnSongTitle(songPos: Int): String {
                return karaokeViewModel.song[songPos]
        }

        fun returnRecordingPermissionCode(): Int {
                return karaokeViewModel.RECORDING_PERMISSION_CODE
        }

        fun returnRecordingPermissionTag(): String {
                return karaokeViewModel.TAG
        }

}