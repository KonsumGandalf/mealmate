package konsum.gandalf.mealmate.evaluation.domain.repository

import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost

interface IEvaluationRepository {
    suspend fun createEvaluation(evaluation: EvaluationPost): EvaluationPost
    suspend fun getEvaluations(): List<EvaluationPost>
    suspend fun getEvaluations(recipeId: String): List<EvaluationPost>
}
