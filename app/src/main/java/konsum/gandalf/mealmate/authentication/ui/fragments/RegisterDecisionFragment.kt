package konsum.gandalf.mealmate.authentication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.constants.FragmentNames
import konsum.gandalf.mealmate.databinding.FragmentRegisterDecisionBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class RegisterDecisionFragment : OneTapFragment() {
	private var _binding: FragmentRegisterDecisionBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentRegisterDecisionBinding.inflate(inflater, container, false)
		fillContent()
		registerButtons()

		return binding.root
	}

	private fun registerButtons() {
		with(binding.registerMethod){
			decisionFrBtnLeft.setOnClickListener{
				Navigation.findNavController(binding.root).navigate(R.id.action_registerDecisionFragment_to_welcomeFragment)
			}
			decisionFrBtnRight.setOnClickListener{
				super.injectOneTap()
			}
		}
	}

	private fun fillContent(){
		with(binding.registerMethod){
			decisionFrIv.setImageResource(konsum.gandalf.mealmate.R.drawable.undraw_fingerprint)
			decisionFrHeader.text = FragmentNames.REGISTER
			decisionFrContainer.backgroundTintList =
				getResources().getColorStateList(konsum.gandalf.mealmate.R.color.red_200)
		}
	}
}