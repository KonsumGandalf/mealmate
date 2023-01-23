package konsum.gandalf.mealmate.recipe.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Area(
    @SerializedName("strArea")
    val name: String
) : Parcelable
