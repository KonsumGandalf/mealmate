package konsum.gandalf.mealmate.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import konsum.gandalf.mealmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}
