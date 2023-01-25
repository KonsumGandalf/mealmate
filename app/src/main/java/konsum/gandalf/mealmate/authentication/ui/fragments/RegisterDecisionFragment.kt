package konsum.gandalf.mealmate.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.constants.FragmentNames
import konsum.gandalf.mealmate.authentication.ui.viewmodels.AuthViewModel
import konsum.gandalf.mealmate.databinding.FragmentRegisterDecisionBinding

@AndroidEntryPoint
class RegisterDecisionFragment : OneTapFragment() {
    private var _binding: FragmentRegisterDecisionBinding? = null
    private val viewModel by viewModels<AuthViewModel>()

    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterDecisionBinding.inflate(inflater, container, false)
        fillContent()
        registerButtons()
        registerObservers()

        return binding.root
    }

    private fun registerButtons() {
        with(binding.registerMethod) {
            decisionFrBtnLeft.setOnClickListener { super.injectOneTap() }
            decisionFrBtnRight.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_registerDecisionFragment_to_registerFragment)
            }
        }
    }

    private fun fillContent() {
        with(binding.registerMethod) {
            decisionFrIv.setImageResource(konsum.gandalf.mealmate.R.drawable.undraw_fingerprint)
            decisionFrHeader.text = FragmentNames.REGISTER
            decisionFrContainer.backgroundTintList =
                getResources().getColorStateList(konsum.gandalf.mealmate.R.color.red_200)
        }
    }

    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                val action =
                    RegisterDecisionFragmentDirections.actionRegisterDecisionFragmentToUserUpdateFragment(
                        user.uid
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
    }
}
