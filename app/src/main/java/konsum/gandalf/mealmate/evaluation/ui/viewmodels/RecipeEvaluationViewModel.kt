package konsum.gandalf.mealmate.evaluation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeEvaluationViewModel
@Inject
constructor(
    private val evaluationsRepository: IEvaluationRepository,
    private val userRepo: IUserRepository
) : ViewModel() {
    private val _filteredEvaluations = MutableLiveData<List<EvaluationPost>?>()
    val recipeEvaluations
        get() = _filteredEvaluations

    private val _user = MutableLiveData<User?>()
    val currentUser
        get() = _user

    fun loadCurrentUser() =
        viewModelScope.launch {
            val user = userRepo.getCurrentUser()
            _user.postValue(user)
        }

    fun getEvaluations(recipeId: String) =
        viewModelScope.launch {
            val recipes = evaluationsRepository.getEvaluations(recipeId)
            _filteredEvaluations.postValue(recipes)
        }
}
