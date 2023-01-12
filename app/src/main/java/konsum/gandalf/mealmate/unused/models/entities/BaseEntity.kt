package konsum.gandalf.mealmate.unused.models.entities

import java.time.LocalDateTime

data class BaseEntity (
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)