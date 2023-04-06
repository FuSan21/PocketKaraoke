package me.fusan.pocketkaraoke.ui.effects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.fusan.pocketkaraoke.databinding.FragmentEffectsBinding
import me.fusan.pocketkaraoke.ui.effects.model.EffectsViewModel

class EffectsFragment : Fragment() {

    private var _binding: FragmentEffectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val effectsViewModel =
            ViewModelProvider(this).get(EffectsViewModel::class.java)

        _binding = FragmentEffectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEffects
        effectsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}