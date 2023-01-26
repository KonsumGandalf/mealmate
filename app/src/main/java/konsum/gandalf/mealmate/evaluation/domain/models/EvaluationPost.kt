package konsum.gandalf.mealmate.evaluation.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.utils.models.FirebaseEntity
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class EvaluationPost(
    @SerializedName(FirebaseReferenceEnum.RECIPE)
    val recipe: Recipe? = null,
    @SerializedName("rating")
    val rating: Float? = null,
    @SerializedName("difficulty")
    var difficulty: Float? = null,
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("ownerId")
    val owner: User? = null,
    val postId: String = UUID.randomUUID().toString()
) : FirebaseEntity(id = postId)
