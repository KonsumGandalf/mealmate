package konsum.gandalf.mealmate.recipe.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.utils.models.FirebaseEntity
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Recipe(
    val recipeId: String = UUID.randomUUID().toString(),
    @SerializedName(FilterFirebaseNamespace.TITLE)
    var title: String = "",
    @SerializedName(FilterFirebaseNamespace.CATEGORY)
    var category: String = "",
    @SerializedName(FilterFirebaseNamespace.AREA)
    var area: String = "",
    @SerializedName("instructions")
    var instructions: String = "",
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("strTags")
    val tags: List<String> = ArrayList<String>(),
    @SerializedName("ingredients")
    var ingredients: List<Ingredient> = ArrayList<Ingredient>(),
    @SerializedName(FilterFirebaseNamespace.OWNER)
    var owner: User? = null,
    /**
     * Not provided by the MealDB
     *
     @SerializedName("totalTime")
     val totalTime: Int = 0,
     @SerializedName("preparationTime")
     val preparationTime: Int = 0,
     @SerializedName("cookTime")
     val cookTime: Int = 0
     */
) : FirebaseEntity(id = recipeId)

/*val servingSize: Int? = 0,
val ingredients: ArrayList<Ingredient> = ArrayList<Ingredient>(),
val preparationTime: Int? = 0,
val cookTime: Int? = 0,
val tags: ArrayList<String> = ArrayList<String>(),
val rating: Float? = 0F,*/
