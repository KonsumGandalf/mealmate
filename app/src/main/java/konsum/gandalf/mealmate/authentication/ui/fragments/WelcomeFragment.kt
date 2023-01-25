package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        with(binding) {
            welcomeBtnLogin.setOnClickListener {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginDecisionFragment()
                Navigation.findNavController(binding.root).navigate(action)
            }
            welcomeBtnRegister.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_welcomeFragment_to_registerDecisionFragment)
            }
        }
        return binding.root
    }
}
