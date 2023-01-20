package konsum.gandalf.mealmate.recipe.data.api.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("idCategory")
    val id: Int,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val imageUrl: String,
    @SerializedName("strCategoryDescription")
    val description: String
)
