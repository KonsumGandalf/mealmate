package konsum.gandalf.mealmate.authentication.data.repository.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import konsum.gandalf.mealmate.authentication.repositories.utils.await

class FirebaseAuthenticator : IAuthenticator {
	override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
		Firebase.auth.createUserWithEmailAndPassword(email,password).await()
		return Firebase.auth.currentUser
	}

	override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
		Firebase.auth.signInWithEmailAndPassword(email , password).await()
		return Firebase.auth.currentUser
	}

	override fun signOut(): FirebaseUser? {
		Firebase.auth.signOut()
		return Firebase.auth.currentUser
	}

	override fun getUser(): FirebaseUser? {
		return Firebase.auth.currentUser
	}

	override suspend fun sendPasswordReset(email: String) {
		Firebase.auth.sendPasswordResetEmail(email).await()
	}

	override suspend fun signingWithCredential(credential: AuthCredential): FirebaseUser? {
		Firebase.auth.signInWithCredential(credential).await()
		return Firebase.auth.currentUser;
	}
}