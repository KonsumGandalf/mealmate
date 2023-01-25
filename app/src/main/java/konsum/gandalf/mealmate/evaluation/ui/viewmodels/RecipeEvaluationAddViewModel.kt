package konsum.gandalf.mealmate.evaluation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.utils.events.CustomEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeEvaluationAddViewModel
@Inject
constructor(
    private val evaluationRepo: IEvaluationRepository
) : ViewModel() {
    private val eventsChannel = Channel<CustomEvent>()
    val eventFlow = eventsChannel.receiveAsFlow()

    fun validateEvaluation(recipeId: String, rating: Float, difficulty: Float, comment: String, user: User) =
        viewModelScope.launch {
            when {
                comment.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(1))
                }
                else -> {
                    createEvaluation(recipeId, rating, difficulty, comment, user)
                }
            }
        }

    private fun createEvaluation(recipeId: String, rating: Float, difficulty: Float, comment: String, user: User) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val eval = EvaluationPost(
                    recipeId,
                    rating,
                    difficulty,
                    comment,
                    user
                )
                evaluationRepo.createEvaluation(eval)
                eventsChannel.send(CustomEvent.Message("Created successfully"))
            }
        }
}
