package konsum.gandalf.mealmate.authentication.data.repository.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthenticator {
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    fun signOut(): FirebaseUser?

    fun getUser(): FirebaseUser?

    suspend fun sendPasswordReset(email: String)

    suspend fun signingWithCredential(credential: AuthCredential): FirebaseUser?
}
