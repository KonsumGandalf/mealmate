package konsum.gandalf.mealmate.recipe.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.utils.models.FirebaseEntity

data class Ingredient(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("measure")
    val measure: String = "",
    val imageUrl: String = "https://www.themealdb.com/images/ingredients/${name.replace(" ","%20")}.png",
    val uniqueId: String = ""
) : FirebaseEntity(id = uniqueId)
