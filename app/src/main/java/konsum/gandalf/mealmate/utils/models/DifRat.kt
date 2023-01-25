package konsum.gandalf.mealmate.utils.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DifRat(
    val rating: Float,
    val difficulty: Float
) : Parcelable
