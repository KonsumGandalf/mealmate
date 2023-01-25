package konsum.gandalf.mealmate.authentication.ui.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.BuildConfig
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: IAuthRepository) : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val currentUser
        get() = _firebaseUser

    private lateinit var _oneTapClient: SignInClient
    val configuredClient
        get() = _oneTapClient
    private lateinit var _signUpRequest: BeginSignInRequest
    val configuredRequest
        get() = _signUpRequest

    private val eventsChannel = Channel<CustomEvent>()
    val toastEventFlow = eventsChannel.receiveAsFlow()

    fun validateCredentialsLogin(email: String, password: String) =
        viewModelScope.launch {
            when {
                email.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(1))
                }
                password.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(2))
                }
                else -> {
                    signInUserMail(email, password)
                }
            }
        }

    fun validateCredentialsRegister(email: String, password: String, confirmPass: String) =
        viewModelScope.launch {
            when {
                email.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(1))
                }
                password.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(2))
                }
                password != confirmPass -> {
                    eventsChannel.send(CustomEvent.ErrorCode(3))
                }
                else -> {
                    registerUserMail(email, password)
                }
            }
        }

    private fun signInUserMail(email: String, password: String) =
        viewModelScope.launch {
            try {
                val user = repository.signInWithEmailPassword(email, password)
                user?.let {
                    _firebaseUser.postValue(it)
                    eventsChannel.send(CustomEvent.Message("login success"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(CustomEvent.Error(error[1]))
            }
        }

    private fun registerUserMail(email: String, password: String) =
        viewModelScope.launch {
            try {
                val user = repository.signUpWithEmailPassword(email, password)
                user?.let {
                    _firebaseUser.postValue(it)
                    eventsChannel.send(CustomEvent.Message("sign up success"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(CustomEvent.Error(error[1]))
            }
        }

    fun configureOneClickClient(activity: FragmentActivity?) =
        viewModelScope.launch {
            activity?.let {
                _oneTapClient = Identity.getSignInClient(it as Activity)
                _signUpRequest =
                    BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(BuildConfig.GOOGLE_SERVER_ID)
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                        )
                        .build()
            }
        }

    fun signingWithGoogle(data: Intent?) =
        viewModelScope.launch {
            try {
                val signInCredential: SignInCredential =
                    configuredClient.getSignInCredentialFromIntent(data)
                when {
                    signInCredential.googleIdToken != null -> {
                        val authCredential: AuthCredential =
                            GoogleAuthProvider.getCredential(signInCredential.googleIdToken, null)
                        val user = repository.signWithCredential(authCredential)
                        _firebaseUser.postValue(user)
                        eventsChannel.send(CustomEvent.Message("sign up success"))
                    }
                    else -> {
                        eventsChannel.send(CustomEvent.Message("No googleId was provided!"))
                    }
                }
            } catch (e: ApiException) {
                eventsChannel.send(CustomEvent.Message(e.localizedMessage!!.toString()))
            }
        }

    fun signOut() =
        viewModelScope.launch {
            try {
                repository.signOut()
                getCurrentUser()
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(CustomEvent.Error(error[1]))
            }
        }

    fun getCurrentUser() =
        viewModelScope.launch {
            val user = repository.getCurrentUserAuth()
            _firebaseUser.postValue(user)
        }

    fun verifySendPasswordReset(email: String) {
        if (email.isEmpty()) {
            viewModelScope.launch { eventsChannel.send(CustomEvent.ErrorCode(1)) }
        } else {
            sendPasswordResetEmail(email)
        }
    }

    private fun sendPasswordResetEmail(email: String) =
        viewModelScope.launch {
            try {
                val result = repository.sendResetPassword(email)
                if (result) {
                    eventsChannel.send(CustomEvent.Message("reset email sent"))
                } else {
                    eventsChannel.send(CustomEvent.Error("could not send password reset"))
                }
            } catch (e: Exception) {
                val error = e.toString().split(":").toTypedArray()
                Log.d(TAG, "signInUser: ${error[1]}")
                eventsChannel.send(CustomEvent.Error(error[1]))
            }
        }
}
