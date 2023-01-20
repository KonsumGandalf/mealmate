package konsum.gandalf.mealmate.recipe.data.api.models

import com.google.gson.annotations.SerializedName

data class AreaResponse(
    @SerializedName("strArea")
    val name: String
)
