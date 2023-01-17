package konsum.gandalf.mealmate.unused.models.entities

data class Post(
    val userId: String,
    val postId: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val comments: List<String>,
    val likes: Int
)
