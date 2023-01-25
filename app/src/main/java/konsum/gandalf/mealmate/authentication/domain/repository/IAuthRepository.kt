package konsum.gandalf.mealmate.authentication.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    fun signOut(): FirebaseUser?

    fun getCurrentUserAuth(): FirebaseUser?

    suspend fun sendResetPassword(email: String): Boolean

    suspend fun signWithCredential(credential: AuthCredential): FirebaseUser?
}
