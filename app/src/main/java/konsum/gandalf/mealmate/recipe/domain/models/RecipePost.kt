package konsum.gandalf.mealmate.recipe.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.utils.models.FirebaseEntity

data class RecipePost(
    val postId: String,
    @SerializedName("rating")
    val rating: Int? = null,
    @SerializedName("difficulty")
    val difficulty: Int? = null,
    @SerializedName("comments")
    val comments: List<String> = ArrayList<String>(),
    @SerializedName("recipe")
    val recipe: Recipe? = null,
    @SerializedName("ownerId")
    val ownerId: String = ""
) : FirebaseEntity(id = postId)
