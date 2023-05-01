package me.fusan.pocketkaraoke.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.databinding.FragmentChallengesBinding
import me.fusan.pocketkaraoke.ui.challenges.model.ChallengesViewModel
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokeViewPresenter

class ChallengesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_challenges, container, false)
        val activity: AppCompatActivity = activity as AppCompatActivity
        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}