package konsum.gandalf.mealmate.user.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.events.CustomEvent
import konsum.gandalf.mealmate.utils.helper.Helper
import konsum.gandalf.mealmate.utils.models.DifRat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@HiltViewModel
class UserProfileViewModel
@Inject
constructor(
    private val repository: IUserRepository,
    private val recipeRepo: IRecipeRepository,
    private val evalRepo: IEvaluationRepository
) : ViewModel() {

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
                loadEvaluations()
            }
        }
    }

    private val _evaluations = MutableLiveData<List<DifRat>>()
    val evaluations
        get() = _evaluations

    private fun loadEvaluations() {
        viewModelScope.launch {
            _recipes.value?.let { recipes ->
                val fullList = ArrayList<DifRat>()
                for (recipe in recipes) {
                    val result = Helper.calculateRatingAndDifficulty(evalRepo.getEvaluations(recipe.recipeId))
                    fullList.add(result)
                }
                _evaluations.postValue(fullList)
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
