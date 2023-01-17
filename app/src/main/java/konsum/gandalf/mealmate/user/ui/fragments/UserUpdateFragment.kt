package konsum.gandalf.mealmate.user.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.authentication.ui.fragments.AuthViewModel
import konsum.gandalf.mealmate.databinding.FragmentUserUpdateBinding
import konsum.gandalf.mealmate.user.ui.viewmodels.UserViewModel
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserUpdateFragment : Fragment() {

    private var _binding: FragmentUserUpdateBinding? = null
    private val binding
        get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val navArgs: UserUpdateFragmentArgs by navArgs()

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var _imageUri: Uri? = null
    private val imageUri
        get() = _imageUri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserUpdateBinding.inflate(inflater, container, false)
        registerActivityForResult()
        initContent()
        initButtons()
        listenToChannels()

        return binding.root
    }

    private fun initContent() {
        val username: String = navArgs.authUserId
        userViewModel.getOrCreateUser(username)
        binding.userUpdateResponseComponent.progressBar.isVisible = false

        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                Picasso.get().load(user.imageUrl).into(binding.userUpdateIv)
                binding.userUpdateNameTi.setText(user.fullName)
                binding.userUpdateUsernameTi.setText(user.username)
                binding.userUpdateDescriptionTi.setText(user.bio)
            }
        }
        binding.userUpdateResponseComponent.progressBar.isVisible = false
    }

    private fun registerActivityForResult() {
        activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result ->
                    if (result.data != null && result.resultCode == AppCompatActivity.RESULT_OK) {
                        lifecycleScope.launch {
                            _imageUri = result.data!!.data!!

                            Picasso.get().load(imageUri).into(binding.userUpdateIv)
                        }
                    }
                }
            )
    }

    private fun createLaunchIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(intent)
    }

    private fun chooseImage() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            createLaunchIntent()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            createLaunchIntent()
        }
    }

    private fun initButtons() {
        with(binding) {
            this.userUpdateIv.setOnClickListener { chooseImage() }
            this.userUpdateBtnConfirm.setOnClickListener {
                userViewModel.navigable.postValue(false)
                userUpdateResponseComponent.progressBar.isVisible = true
                userViewModel.validateCredentialInput(
                    imageUri,
                    binding.userUpdateUsernameTi.text.toString(),
                    binding.userUpdateNameTi.text.toString(),
                    binding.userUpdateDescriptionTi.text.toString()
                )
            }
            this.userUpdateBtnCancel.setOnClickListener {
                authViewModel.signOut()
                userViewModel.signOut()
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_userUpdateFragment_to_welcomeFragment)
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.eventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Message -> {
                        Toast.makeText(this@UserUpdateFragment.context, event.message, Toast.LENGTH_SHORT)
                            .show()
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_userUpdateFragment_to_userProfileFragment)
                    }
                    is CustomEvent.Error -> {
                        binding.apply {
                            userUpdateResponseComponent.errorTxtHelper.text = event.error
                            userUpdateResponseComponent.progressBar.isInvisible = true
                        }
                    }
                    is CustomEvent.ErrorCode -> {
                        binding.userUpdateResponseComponent.progressBar.isInvisible = true
                        when (event.code) {
                            1 -> {
                                binding.apply { userUpdateUsernameTi.error = "username should not be empty" }
                            }
                            2 -> {
                                binding.apply { userUpdateUsernameTi.error = "username length is to long" }
                            }
                            3 -> {
                                binding.apply { userUpdateNameTi.error = "full name should not be empty" }
                            }
                            4 -> {
                                binding.apply { userUpdateNameTi.error = "full name length is to long" }
                            }
                            5 -> {
                                binding.apply { userUpdateDescriptionTi.error = "biography length is to long" }
                            }
                            6 -> {
                                binding.apply { userUpdateUsernameTi.error = "username already taken" }
                            }
                        }
                    }
                }
            }
        }
    }
}
