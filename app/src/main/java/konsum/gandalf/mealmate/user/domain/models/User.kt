package konsum.gandalf.mealmate.user.domain.models

import com.google.firebase.database.PropertyName
import konsum.gandalf.mealmate.user.domain.constants.UserPropertyNames
import konsum.gandalf.mealmate.utils.models.FirebaseEntity
import java.util.*

class User(
    @PropertyName(UserPropertyNames.username) var username: String? = null,
    @PropertyName("fullName") var fullName: String? = null,
    @PropertyName(UserPropertyNames.id) override val id: String = UUID.randomUUID().toString(),
    @PropertyName("firebaseMail") val firebaseMail: String? = null,
    @PropertyName("imageUrl") var imageUrl: String? = null,
    @PropertyName("rating") var bio: String? = null,
    @PropertyName("rating") val rating: Double? = null
	/*val posts: List<Post>?,
	val recipes: List<Recipe>,
	val friends: List<User>,
	val conversations: List<Conversation>*/
) : FirebaseEntity()
