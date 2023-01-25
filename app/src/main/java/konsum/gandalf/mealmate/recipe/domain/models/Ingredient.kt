package konsum.gandalf.mealmate.recipe.domain.models

import com.google.gson.annotations.SerializedName
import konsum.gandalf.mealmate.utils.models.FirebaseEntity

data class Ingredient(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("measure")
    var measure: String = "",
    var imageUrl: String = getImageUrl(name),
    val uniqueId: String = ""
) : FirebaseEntity(id = uniqueId) {
    companion object {
        fun getImageUrl(name: String): String {
            return "https://www.themealdb.com/images/ingredients/${name.replace(" ","%20")}.png"
        }
    }
}
