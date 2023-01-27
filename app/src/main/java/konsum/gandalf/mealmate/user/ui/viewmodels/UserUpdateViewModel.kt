package konsum.gandalf.mealmate.user.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.events.CustomEvent
import konsum.gandalf.mealmate.utils.repositories.images.IImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserUpdateViewModel
@Inject
constructor(
    private val repository: IUserRepository,
    private val imageRepo: IImageRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val currentUser
        get() = _user

    private fun getCurrentUser() =
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            _user.postValue(user)
        }

    private val _navigable = MutableLiveData<Boolean?>()
    val navigable
        get() = _navigable

    private val eventsChannel = Channel<CustomEvent>()
    val eventFlow = eventsChannel.receiveAsFlow()

    fun validateCredentialInput(imageUri: Uri?, username: String, fullName: String, bio: String) = viewModelScope.launch {
        when {
            username.isEmpty() -> {
                eventsChannel.send(CustomEvent.ErrorCode(1))
            }
            (username.length > 10) -> {
                eventsChannel.send(CustomEvent.ErrorCode(2))
            }
            fullName.isEmpty() -> {
                eventsChannel.send(CustomEvent.ErrorCode(3))
            }
            (fullName.length > 24) -> {
                eventsChannel.send(CustomEvent.ErrorCode(4))
            }
            (bio.length > 250) -> {
                eventsChannel.send(CustomEvent.ErrorCode(5))
            }
            else -> {
                updateUser(imageUri, username, fullName, bio)
            }
        }
    }

    fun getOrCreateUser(uid: String) =
        viewModelScope.launch {
            if (_user.value == null) {
                val user = repository.getUser(uid) ?: repository.createUser()
                _user.postValue(user)
            }
        }

    private fun updateUser(uri: Uri?, username: String, fullName: String, bio: String) = viewModelScope.launch {
        _user.value?.let { user ->
            withContext(Dispatchers.IO) {
                uri?.let {
                    val urlOfUploadedImage = imageRepo.uploadImg(user.id, it)
                    user.imageUrl = urlOfUploadedImage
                }

                user.fullName = fullName.trim()
                user.username = username.trim()
                user.bio = bio.trim()
                repository.updateUser(user)
                navigable.postValue(true)
                navigable.postValue(false)
                eventsChannel.send(CustomEvent.Message("Updated successfully"))
            }
        }
    }

    fun signOut() =
        viewModelScope.launch {
            getCurrentUser()
            repository.signOut()?.let {
                eventsChannel.send(CustomEvent.Message("logout failure"))
            }
        }
}
