package konsum.gandalf.mealmate.authentication.ui.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import konsum.gandalf.mealmate.authentication.ui.utils.ToastEvent


@HiltViewModel
class AuthViewModel @Inject constructor(
	private val repository : IAuthRepository
) : ViewModel() {

	private val TAG = "AuthViewModel"

	/**This is a ViewModel class and is responsible for the logic of all ui.
	 * It shall be shared with the three fragments.
	 * Only share ViewModels when the fragments share a feature or functionality */

	//create the auth state livedata object that will be passed to
	//the home fragment and shall be used to control the ui i.e show authentication state
	//control behaviour of sign in and sign up button
	private val _firebaseUser = MutableLiveData<FirebaseUser?>()
	val currentUser get() = _firebaseUser

	//create our channels that will be used to pass messages to the main ui
	//create event channel
	private val eventsChannel = Channel<ToastEvent>()
	//the messages passed to the channel shall be received as a Flowable
	//in the ui
	val toastEventFlow = eventsChannel.receiveAsFlow()


	//validate all fields first before performing any sign in operations
	fun validateCredentialsLogin(email: String, password: String) = viewModelScope.launch{
		when {
			email.isEmpty() -> {
				eventsChannel.send(ToastEvent.ErrorCode(1))
			}
			password.isEmpty() -> {
				eventsChannel.send(ToastEvent.ErrorCode(2))
			}
			else -> {
				signInUser(email , password)
			}
		}
	}

	//validate all fields before performing any sign up operations
	fun validateCredentialsRegister(email : String, password: String, confirmPass : String)= viewModelScope.launch {
		when{
			email.isEmpty() -> {
				eventsChannel.send(ToastEvent.ErrorCode(1))
			}
			password.isEmpty() -> {
				eventsChannel.send(ToastEvent.ErrorCode(2))
			}
			password != confirmPass ->{
				eventsChannel.send(ToastEvent.ErrorCode(3))
			}
			else -> {
				registerUser(email, password)
			}
		}
	}


	private fun signInUser(email:String, password: String) = viewModelScope.launch {
		try {
			val user = repository.signInWithEmailPassword(email, password)
			user?.let {
				_firebaseUser.postValue(it)
				eventsChannel.send(ToastEvent.Message("login success"))
			}
		}catch(e:Exception){
			val error = e.toString().split(":").toTypedArray()
			Log.d(TAG, "signInUser: ${error[1]}")
			eventsChannel.send(ToastEvent.Error(error[1]))
		}
	}

	private fun registerUser(email:String, password: String) = viewModelScope.launch {
		try {
			val user = repository.signUpWithEmailPassword(email, password)
			user?.let {
				_firebaseUser.postValue(it)
				eventsChannel.send(ToastEvent.Message("sign up success"))
			}
		}catch(e:Exception){
			val error = e.toString().split(":").toTypedArray()
			Log.d(TAG, "signInUser: ${error[1]}")
			eventsChannel.send(ToastEvent.Error(error[1]))
		}
	}

	fun signOut() = viewModelScope.launch {
		try {
			val user = repository.signOut()
			user?.let {
				//eventsChannel.send(ToastEvent.Message("logout failure"))
			}?: eventsChannel.send(ToastEvent.Message("sign out successful"))

			getCurrentUser()

		}catch(e:Exception){
			val error = e.toString().split(":").toTypedArray()
			Log.d(TAG, "signInUser: ${error[1]}")
			eventsChannel.send(ToastEvent.Error(error[1]))
		}
	}

	fun getCurrentUser() = viewModelScope.launch {
		val user = repository.getCurrentUser()
		_firebaseUser.postValue(user)
	}

	fun verifySendPasswordReset(email: String){
		if(email.isEmpty()){
			viewModelScope.launch {
				eventsChannel.send(ToastEvent.ErrorCode(1))
			}
		}else{
			sendPasswordResetEmail(email)
		}

	}

	private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
		try {
			val result = repository.sendResetPassword(email)
			if (result){
				eventsChannel.send(ToastEvent.Message("reset email sent"))
			}else{
				eventsChannel.send(ToastEvent.Error("could not send password reset"))
			}
		}catch (e : Exception){
			val error = e.toString().split(":").toTypedArray()
			Log.d(TAG, "signInUser: ${error[1]}")
			eventsChannel.send(ToastEvent.Error(error[1]))
		}
	}

	
}
