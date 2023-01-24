package konsum.gandalf.mealmate.user.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel
@Inject
constructor(private val repository: IUserRepository, private val recipeRepo: IRecipeRepository) :
    ViewModel() {

    private val eventsChannel = Channel<CustomEvent>()

    private val _user = MutableLiveData<User?>()
    val currentUser
        get() = _user

    private fun getCurrentUser() =
        viewModelScope.launch {
            val user = repository.getCurrentUser()
            _user.postValue(user)
        }

    private val _recipes = MutableLiveData<List<Recipe>?>()
    val userRecipes
        get() = _recipes

    fun getRecipes() {
        viewModelScope.launch {
            _user.value?.let { user ->
                val recipes = recipeRepo.getUserRecipes(user.uid)
                _recipes.postValue(recipes)
            }
        }
    }

    fun signOut() =
        viewModelScope.launch {
            getCurrentUser()
            repository.signOut()?.let { eventsChannel.send(CustomEvent.Message("logout successfully")) }
        }

    fun getOrCreateUser(uid: String) =
        viewModelScope.launch {
            if (_user.value == null) {
                val user = repository.getUser(uid) ?: repository.createUser()
                _user.postValue(user)
                val recipes = recipeRepo.getUserRecipes(user!!.uid)
                _recipes.postValue(recipes)
            }
        }
}
