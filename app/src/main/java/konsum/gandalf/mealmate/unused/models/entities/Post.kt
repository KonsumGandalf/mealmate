package konsum.gandalf.mealmate.unused.models.entities

import java.time.LocalDateTime

data class Post(
    val userId: String,
    val postId: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val comments: List<String>,
    val likes: Int
)