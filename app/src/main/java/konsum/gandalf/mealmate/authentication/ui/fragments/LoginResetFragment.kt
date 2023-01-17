package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.FragmentLoginResetBinding
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginResetFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()
    val navArgs: LoginResetFragmentArgs by navArgs()
    private lateinit var _binding: FragmentLoginResetBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginResetBinding.inflate(inflater, container, false)

        listenToChannels()
        fillContent()
        registerButtons()

        return binding.root
    }

    private fun fillContent() {
        binding.apply {
            loginResetTiMail.setText(navArgs.loginMail)
            loginResponseComponent.progressBar.isVisible = false
        }
    }

    private fun registerButtons() {
        binding.apply {
            loginResetBtn.setOnClickListener {
                loginResponseComponent.progressBar.isVisible = true
                authViewModel.verifySendPasswordReset(loginResetTiMail.text.toString())
                loginResponseComponent.progressBar.isVisible = false
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.toastEventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginResetFragment_to_welcomeFragment)
                    }
                    is CustomEvent.Error -> {
                        binding.apply {
                            loginResponseComponent.progressBar.isInvisible = true
                            loginResponseComponent.errorTxtHelper.text = event.error
                        }
                    }
                    is CustomEvent.ErrorCode -> {
                        if (event.code == 1) {
                            binding.apply {
                                loginResponseComponent.errorTxtHelper.text = "email should not be empty!"
                                loginResponseComponent.progressBar.isInvisible = true
                            }
                        }
                    }
                }
            }
        }
    }
}
