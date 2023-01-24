package konsum.gandalf.mealmate.user.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel
@Inject
constructor(
    private val userRepo: IUserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val currentUser
        get() = _user

    fun loadCurrentUser() =
        viewModelScope.launch {
            val user = userRepo.getCurrentUser()
            _user.postValue(user)
        }
}
