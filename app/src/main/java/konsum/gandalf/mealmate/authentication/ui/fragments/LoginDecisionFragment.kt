package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.HiltAndroidApp
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentLoginDecisionBinding
import konsum.gandalf.mealmate.authentication.ui.constants.FragmentNames

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginDecisionFragment : Fragment() {
	private var _binding: FragmentLoginDecisionBinding? = null

	val args: LoginDecisionFragmentArgs by navArgs()

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentLoginDecisionBinding.inflate(inflater, container, false)

		val numberArg = args.incomingTest
		Toast.makeText(context, args.incomingTest.toString(), Toast.LENGTH_LONG).show()

		with(binding.loginMethod){
			decisionFrIv.setImageResource(R.drawable.undraw_mobile_login)
			decisionFrHeader.text = FragmentNames.LOGIN
			decisionFrContainer.backgroundTintList =
				getResources().getColorStateList(R.color.blue_200)
			decisionFrBtnLeft.setOnClickListener{
				Navigation.findNavController(binding.root).navigate(R.id.action_loginDecisionFragment_to_welcomeFragment)
			}
			decisionFrBtnRight.setOnClickListener{
				Navigation.findNavController(binding.root).navigate(R.id.action_loginDecisionFragment_to_loginFragment)
			}
		}

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}
}


