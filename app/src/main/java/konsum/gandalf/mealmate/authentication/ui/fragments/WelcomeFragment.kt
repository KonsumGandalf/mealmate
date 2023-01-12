package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentWelcomeBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e. status bar and
 * navigation/system bar) with user interaction.
 */
class WelcomeFragment : Fragment() {
	private var _binding: FragmentWelcomeBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentWelcomeBinding.inflate(inflater, container, false)
		with(binding) {
			welcomeBtnLogin.setOnClickListener {
				// val action = Welcome
				val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginDecisionFragment(23)
				Navigation.findNavController(binding.root).navigate(action)
			}
			welcomeBtnRegister.setOnClickListener {
				Navigation.findNavController(binding.root).navigate(R.id.action_welcomeFragment_to_registerDecisionFragment)
			}
		}
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}
}
