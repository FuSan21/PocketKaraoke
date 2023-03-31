package me.fusan.pocketkaraoke.ui.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.fusan.pocketkaraoke.databinding.FragmentChallengesBinding

class ChallengesFragment : Fragment() {

    private var _binding: FragmentChallengesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val challengesViewModel =
            ViewModelProvider(this).get(ChallengesViewModel::class.java)

        _binding = FragmentChallengesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textChallenges
        challengesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}