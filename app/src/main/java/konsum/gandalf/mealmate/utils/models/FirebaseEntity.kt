package konsum.gandalf.mealmate.utils.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
open class FirebaseEntity(
    @PropertyName("id") open val id: String? = UUID.randomUUID().toString(),
    @PropertyName("createdAt")
    val createdAt: String =
        SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
) : Parcelable
