package konsum.gandalf.mealmate.authentication.ui.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class OneTapFragment : Fragment() {

    val REQUEST_CODE_SIGNING = 78921
    private val viewModel by viewModels<AuthViewModel>()

    override fun onStart() {
        super.onStart()
        listenToChannels()
    }
    protected fun injectOneTap() {
        viewModel.configureOneClickClient(activity)
        renderOneTapUI()
    }

    @Deprecated("Deprecated in Java")
    private fun renderOneTapUI() {
        viewModel.configuredClient
            .beginSignIn(viewModel.configuredRequest)
            .addOnSuccessListener(activity as Activity) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender,
                        REQUEST_CODE_SIGNING,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Toast.makeText(context, "The OneTap UI could not be rendered", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener(activity as Activity) { e ->
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SIGNING -> {
                viewModel.signingWithGoogle(data)
            }
        }
    }

    private fun listenToChannels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toastEventFlow.collect { event ->
                when (event) {
                    is CustomEvent.Message -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d(ContentValues.TAG, "listenToChannels: No event received so far")
                    }
                }
            }
        }
    }
}
