package konsum.gandalf.mealmate.evaluation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import kotlinx.coroutines.launch

@HiltViewModel
class HomeEvaluationViewModel
@Inject
constructor(
    private val evaluationsRepository: IEvaluationRepository
) : ViewModel() {
    private val _allEvaluations = MutableLiveData<List<EvaluationPost>?>()
    val recipeEvaluations
        get() = _allEvaluations

    fun getEvaluations() =
        viewModelScope.launch {
            val recipes = evaluationsRepository.getEvaluations()
            _allEvaluations.postValue(recipes)
        }
}
