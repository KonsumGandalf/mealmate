package konsum.gandalf.mealmate.authentication.data.repository.firebase

import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface IAuthenticator {

	//this is an interface that will implement all common authentication
	//functions. That is sign in  , sign up , logout. Taking this approach will allow us
	// to rely on abstractions rather than a concrete authentication repository class. This
	//will make it easy for us to test and maintain in that whatever form of repository class
	//we will use it won't matter since all will inherit from this class. So swapping
	//of repositories will be easy.

	suspend fun signInWithEmailPassword(email:String , password:String): FirebaseUser?

	suspend fun signUpWithEmailPassword(email: String , password: String): FirebaseUser?

	fun signOut() : FirebaseUser?

	fun getUser() : FirebaseUser?

	suspend fun sendPasswordReset(email :String)

	suspend fun signingWithCredential(credential: AuthCredential): FirebaseUser?
}