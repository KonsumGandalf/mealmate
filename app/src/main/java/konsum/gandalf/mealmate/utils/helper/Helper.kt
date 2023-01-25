package konsum.gandalf.mealmate.utils.helper

import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.utils.models.DifRat

object Helper {
    fun calculateRatingAndDifficulty(evalList: List<EvaluationPost>): DifRat {
        var diff = 0f
        var rating = 0f
        evalList.let { evals ->
            evals.let {
                for (post in evals) {
                    diff += post.difficulty!!
                    rating += post.rating!!
                }
                diff /= evals.size
                rating /= evals.size
            }
        }
        return DifRat(rating, diff)
    }
}
