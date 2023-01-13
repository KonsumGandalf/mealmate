package konsum.gandalf.mealmate.authentication.ui.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.constants.FragmentNames
import konsum.gandalf.mealmate.authentication.ui.utils.ToastEvent
import konsum.gandalf.mealmate.databinding.FragmentLoginDecisionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * An example full-screen fragment that shows and hides the system UI (i.e. status bar and
 * navigation/system bar) with user interaction.
 */
@AndroidEntryPoint
class LoginDecisionFragment : OneTapFragment() {
	private var _binding: FragmentLoginDecisionBinding? = null
	private val viewModel by viewModels<AuthViewModel>()


	/*private val oneTapClient: SignInClient by lazy {
		Identity.getSignInClient(activity as Activity)
	}*/

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding
		get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentLoginDecisionBinding.inflate(inflater, container, false)
		fillContent()
		registerButtons()

		return binding.root
	}

	private fun registerButtons() {
		with(binding.loginMethod) {
			decisionFrBtnLeft.setOnClickListener {
				Navigation.findNavController(binding.root)
					.navigate(R.id.action_loginDecisionFragment_to_welcomeFragment)
			}
			decisionFrBtnRight.setOnClickListener {
				super.injectOneTap()
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			super.REQUEST_CODE_SIGNING -> {
				viewModel.signingWithGoogle(activity, data)
			}
		}
	}

	private fun fillContent() {
		with(binding.loginMethod) {
			decisionFrIv.setImageResource(R.drawable.undraw_mobile_login)
			decisionFrHeader.text = FragmentNames.LOGIN
			decisionFrContainer.backgroundTintList = getResources().getColorStateList(R.color.blue_200)
		}
	}

}
