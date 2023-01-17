package konsum.gandalf.mealmate.authentication.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.constants.FragmentNames
import konsum.gandalf.mealmate.databinding.FragmentLoginDecisionBinding

@AndroidEntryPoint
class LoginDecisionFragment : OneTapFragment() {
    private var _binding: FragmentLoginDecisionBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginDecisionBinding.inflate(inflater, container, false)
        fillContent()
        registerButtons()
        registerObservers()

        return binding.root
    }

    private fun registerButtons() {
        with(binding.loginMethod) {
            decisionFrBtnLeft.setOnClickListener {
                super.injectOneTap()
            }
            decisionFrBtnRight.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_loginDecisionFragment_to_loginFragment)
            }
        }
    }

    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val action =
                    LoginDecisionFragmentDirections.actionLoginDecisionFragmentToUserUpdateFragment(
                        user.uid
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            super.REQUEST_CODE_SIGNING -> {
                viewModel.signingWithGoogle(data)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun fillContent() {
        with(binding.loginMethod) {
            decisionFrIv.setImageResource(R.drawable.undraw_mobile_login)
            decisionFrHeader.text = FragmentNames.LOGIN
            decisionFrContainer.backgroundTintList = resources.getColorStateList(R.color.blue_200)
        }
    }
}
