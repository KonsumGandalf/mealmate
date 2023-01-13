package konsum.gandalf.mealmate.authentication.repositories

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.authentication.data.repository.firebase.IAuthenticator
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authenticator : IAuthenticator
) : IAuthRepository {
	override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
		return authenticator.signInWithEmailPassword(email , password)
	}

	override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
		return authenticator.signUpWithEmailPassword(email , password)
	}

	override fun signOut(): FirebaseUser? {
		return authenticator.signOut()
	}

	override fun getCurrentUser(): FirebaseUser? {
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

