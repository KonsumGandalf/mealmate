package konsum.gandalf.mealmate.user.domain.repository

import konsum.gandalf.mealmate.user.domain.models.User

interface IUserRepository {
    fun getCurrentUser(): User?
    fun signOut(): User?
    suspend fun updateUser(user: User): User?
    suspend fun createUser(): User?
    suspend fun deleteUser(uid: String)
    suspend fun getUser(uid: String): User?
}
