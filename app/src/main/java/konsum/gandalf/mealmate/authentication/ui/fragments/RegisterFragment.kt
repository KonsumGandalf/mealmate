package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentRegisterBinding
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel by viewModels<AuthViewModel>()

    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerResponseComponent.progressBar.isVisible = false

        registerObservers()
        registerButtons()
        listenToChannels()

        return binding.root
    }

    private fun registerButtons() {
        with(binding) {
            registerBtnConfirm.setOnClickListener {
                viewModel.validateCredentialsRegister(
                    registerTiMail.text.toString(),
                    registerTiPassword.text.toString(),
                    registerTiPasswordConfirm.text.toString()
                )

                registerResponseComponent.progressBar.isVisible = true
                registerResponseComponent.errorTxtHelper.isVisible = false
                viewModel.currentUser.observe(viewLifecycleOwner) { user ->
                    user?.let {
                        binding?.apply { Toast.makeText(context, "hi $it.email", Toast.LENGTH_LONG).show() }
                    }
                }
            }
        }
    }

    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val action =
                    RegisterFragmentDirections.actionRegisterFragmentToUserUpdateFragment(
                        user.uid
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Error -> {
                        binding?.apply { registerResponseComponent.errorTxtHelper.text = event.error }
                    }
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is CustomEvent.ErrorCode -> {
                        when (event.code) {
                            1 -> {
                                binding?.apply { registerTiMailContainer.error = "email should not be empty" }
                            }
                            2 -> {
                                binding?.apply {
                                    registerTiPasswordContainer.error = "password should not be empty"
                                }
                            }
                            3 -> {
                                binding?.apply {
                                    registerTiPasswordContainerConfirm.error = "passwords do not match"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
