package konsum.gandalf.mealmate.authentication.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import konsum.gandalf.mealmate.authentication.data.repository.firebase.IAuthenticator
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository

class AuthRepositoryImpl @Inject constructor(private val authenticator: IAuthenticator) :
    IAuthRepository {
    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signInWithEmailPassword(email, password)
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signUpWithEmailPassword(email, password)
    }

    override fun signOut(): FirebaseUser? {
        return authenticator.signOut()
    }

    override fun getCurrentUserAuth(): FirebaseUser? {
        return authenticator.getUser()
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        authenticator.sendPasswordReset(email)
        return true
    }

    override suspend fun signWithCredential(credential: AuthCredential): FirebaseUser? {
        return authenticator.signingWithCredential(credential)
    }
}
