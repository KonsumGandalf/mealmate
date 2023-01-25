package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.viewmodels.AuthViewModel
import konsum.gandalf.mealmate.databinding.FragmentLoginBinding
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: AuthViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginResponseComponent.progressBar.isVisible = false

        listenToChannels()
        registerButtons()
        registerObservers()

        return binding.root
    }

    private fun registerButtons() {
        with(binding) {
            loginBtnConfirm.setOnClickListener {
                viewModel.validateCredentialsLogin(
                    loginTiMail.text.toString(),
                    loginTiPassword.text.toString()
                )
                loginResponseComponent.progressBar.isVisible = true
            }
            loginBtnForgot.setOnClickListener {
                val mailString = loginTiMail.text.toString()
                val toLoginReset =
                    LoginFragmentDirections.actionLoginFragmentToLoginResetFragment(mailString)
                Navigation.findNavController(binding.root).navigate(toLoginReset)
            }
        }
    }

    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val action =
                    LoginFragmentDirections.actionLoginFragmentToUserUpdateFragment(user.uid)
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Error -> {
                        binding.apply { loginResponseComponent.errorTxtHelper.text = event.error }
                    }
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is CustomEvent.ErrorCode -> {
                        when (event.code) {
                            1 -> {
                                binding.apply { loginTiMailContainer.error = "email should not be empty" }
                            }
                            2 -> {
                                binding.apply { loginTiPasswordContainer.error = "password should not be empty" }
                            }
                        }
                    }
                }
            }
        }
    }
}
