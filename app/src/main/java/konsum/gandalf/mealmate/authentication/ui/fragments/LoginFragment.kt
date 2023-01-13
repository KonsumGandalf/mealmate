package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.utils.ToastEvent
import konsum.gandalf.mealmate.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
	private var _binding: FragmentLoginBinding? = null

	private val viewModel: AuthViewModel by activityViewModels()
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentLoginBinding.inflate(inflater, container, false)
		binding.loginResponseComponent.progressBar.isVisible = false;

		listenToChannels()
		registerButtons()
		registerObservers()

		return binding.root
	}

	private fun registerButtons() {
		with(binding){
			loginBtnConfirm.setOnClickListener {
				viewModel.validateCredentialsLogin(loginTiMail.text.toString(), loginTiPassword.text.toString())
				loginResponseComponent.progressBar.isVisible = true;
			}
			loginBtnForgot.setOnClickListener {
				val mailString = loginTiMail.text.toString()
				val toLoginReset = LoginFragmentDirections.actionLoginFragmentToLoginResetFragment(mailString);
				Navigation.findNavController(binding.root).navigate(toLoginReset);
			}
		}
	}

	private fun registerObservers() {
		viewModel.currentUser.observe(viewLifecycleOwner, { user ->
			user?.let {
				Toast.makeText(context, it.displayName.toString(), Toast.LENGTH_SHORT).show()
			}
		})
	}

	private fun listenToChannels() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.toastEventFlow.collect { event ->
				when (event) {
					is ToastEvent.Error -> {
						binding?.apply { loginResponseComponent.errorTxtHelper.text = event.error }
					}
					is ToastEvent.Message -> {
						Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
					}
					is ToastEvent.ErrorCode -> {
						when (event.code) {
							1 -> {
								binding?.apply { loginTiMailContainer.error = "email should not be empty" }
							}
							2 -> {
								binding?.apply {
									loginTiPasswordContainer.error = "password should not be empty"
								}
							}
						}
					}
				}
			}
		}
	}
}