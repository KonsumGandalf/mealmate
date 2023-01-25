package konsum.gandalf.mealmate.utils.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
open class FirebaseEntity(
    @PropertyName("id") open var id: String = UUID.randomUUID().toString(),
    @PropertyName("createdAt")
    val createdAt: String =
        SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
) : Parcelable
