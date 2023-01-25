package konsum.gandalf.mealmate.user.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.viewmodels.AuthViewModel
import konsum.gandalf.mealmate.databinding.FragmentUserProfileBinding
import konsum.gandalf.mealmate.user.ui.adapter.RecipeUserAdapter
import konsum.gandalf.mealmate.user.ui.viewmodels.UserProfileViewModel

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding
        get() = _binding!!
    private val userViewModel by viewModels<UserProfileViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        registerChange()
        registerFirebaseAccount()
        registerUser()
        registerUserRecipes()
        userViewModel.getRecipes()

        return binding.root
    }

    private fun registerFirebaseAccount() {
        authViewModel.getCurrentUser()
        authViewModel.currentUser.observe(viewLifecycleOwner) { authUser ->
            userViewModel.currentUser.value.let {
                if (authUser != null) {
                    userViewModel.getOrCreateUser(authUser.uid)
                } else {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_userProfileFragment_to_welcomeFragment)
                }
            }
        }
    }

    private fun registerUser() {
        userViewModel.currentUser.observe(viewLifecycleOwner) { it ->
            it?.let { user ->
                binding.apply {
                    userProfileUsernameTv.text = user.username
                    userProfileBioTv.text = user.bio
                    Picasso.get().load(user.imageUrl).into(binding.userProfileIv)
                    userProfileBtnLogout.setOnClickListener {
                        userViewModel.signOut()
                        authViewModel.signOut()
                    }
                    userProfileBtnEdit.setOnClickListener {
                        val action =
                            UserProfileFragmentDirections.actionUserProfileFragmentToUserUpdateFragment(user.id)
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                }
            }
        }
    }

    private fun registerUserRecipes() {
        userViewModel.evaluations.observe(viewLifecycleOwner) { evaluations ->
            evaluations?.let { evals ->
                binding.userProfileRecipesRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.userProfileRecipesRv.adapter = userViewModel.userRecipes.value?.let { recipes ->
                    RecipeUserAdapter(recipes, evals)
                } ?: RecipeUserAdapter()
            }
        }
    }

    private fun registerChange() {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() // add this
        fadeIn.duration = 1000

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator() // and this
        fadeOut.startOffset = 1000
        fadeOut.duration = 1000

        binding.userProfileCard.setOnClickListener {
            if (binding.userProfileIv.isVisible == true) {
                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_right))
                binding.userProfileIv.isVisible = false
                binding.userProfileFollowerLabelTv.isVisible = false
                binding.userProfileFollowerTv.isVisible = false
                binding.userProfileRatingLabelTv.isVisible = false
                binding.userProfileRatingTv.isVisible = false
                binding.userProfileUsernameTv.isVisible = false

                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_left))
                binding.userProfileBioLabelTv.isVisible = true
                binding.userProfileBioTv.isVisible = true
            } else {
                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out_right))
                binding.userProfileIv.isVisible = true
                binding.userProfileFollowerLabelTv.isVisible = true
                binding.userProfileFollowerTv.isVisible = true
                binding.userProfileRatingLabelTv.isVisible = true
                binding.userProfileRatingTv.isVisible = true
                binding.userProfileUsernameTv.isVisible = true

                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in_left))
                binding.userProfileBioLabelTv.isVisible = false
                binding.userProfileBioTv.isVisible = false
            }
        }
    }
}
