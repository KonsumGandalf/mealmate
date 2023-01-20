package konsum.gandalf.mealmate.recipe.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.utils.models.FirebaseEntity

data class Ingredient(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("measure")
    val measure: String = "",
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    val uniqueId: String = ""
) : FirebaseEntity(id = uniqueId)
