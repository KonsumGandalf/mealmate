package konsum.gandalf.mealmate.user.domain.models

import com.google.firebase.auth.FirebaseUser

data class User(
	val userId: String? = null,
	val username: String? = null,
	val fullName: String? = null,
	val firebaseUser: FirebaseUser? = null,
	val imageUrl: String? = null,
	val bio: String? = null,
	val rating: Double? = null,
	/*val posts: List<Post>?,
	val recipes: List<Recipe>,
	val friends: List<User>,
	val conversations: List<Conversation>*/
)