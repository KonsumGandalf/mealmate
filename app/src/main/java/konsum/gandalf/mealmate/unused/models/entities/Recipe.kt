package konsum.gandalf.mealmate.unused.models.entities

import com.google.firebase.database.Exclude
import java.util.UUID

data class Recipe(
    val recipeId: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val totalTime: Int = 0,
    /*val servingSize: Int? = 0,
    val ingredients: ArrayList<Ingredient> = ArrayList<Ingredient>(),
    val instructions: ArrayList<String> = ArrayList<String>(),
    val preparationTime: Int? = 0,
    val cookTime: Int? = 0,
    val tags: ArrayList<String> = ArrayList<String>(),
    val rating: Float? = 0F,*/
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "recipeId" to recipeId,
            "description" to description,
            "title" to title,
            "totalTime" to totalTime,
            "imageUrl" to imageUrl,
        )
    }
}
