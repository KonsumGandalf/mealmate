package konsum.gandalf.mealmate.utils.models

import com.google.firebase.database.PropertyName
import java.text.SimpleDateFormat
import java.util.*

open class FirebaseEntity(
    @PropertyName("id") open val id: String? = UUID.randomUUID().toString(),
    @PropertyName("createdAt")
    val createdAt: String =
        SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
)
