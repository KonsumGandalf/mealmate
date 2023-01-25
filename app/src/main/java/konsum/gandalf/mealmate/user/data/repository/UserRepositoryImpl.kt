package konsum.gandalf.mealmate.user.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.user.domain.constants.FirebaseReferenceEnum
import konsum.gandalf.mealmate.user.domain.models.User
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.await
import konsum.gandalf.mealmate.utils.repositories.images.constants.MealMateStaticData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val authRepo: IAuthRepository,
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
    private val userCollection: CollectionReference =
        FirebaseFirestore.getInstance().collection(FirebaseReferenceEnum.USER)
) : IUserRepository {
    private var _currentUser: User? = null
    private val TAG = "UserRepositoryImpl"

    override fun getCurrentUser(): User? {
        return _currentUser
    }

    override fun signOut(): User? {
        val temp = _currentUser
        _currentUser = null
        return temp
    }

    override suspend fun updateUser(user: User): User? {
        return withContext(ioDispatchers) {
            userCollection
                .document(user.id)
                .set(user, SetOptions.merge())
                .addOnSuccessListener { _currentUser = user }
                .addOnFailureListener { Log.d(TAG, it.localizedMessage!!.toString()) }
                .await()
            getCurrentUser()
        }
    }

    override suspend fun createUser(): User? {
        return withContext(ioDispatchers) {
            val authUser = authRepo.getCurrentUserAuth()
            val uniqueId = UUID.randomUUID().toString()
            val randomName = "${authUser?.displayName + "-"}$uniqueId".substring(0, 24)

            val blankUser =
                User(
                    randomName,
                    authUser?.displayName,
                    authUser!!.uid,
                    authUser.email,
                    MealMateStaticData.ICON_LINK,
                    "",
                    0.0
                )
            userCollection
                .document(authUser.uid)
                .set(blankUser)
                .addOnSuccessListener {}
                .addOnFailureListener { Log.d(TAG, it.localizedMessage!!.toString()) }
                .await()
            _currentUser = blankUser
            getCurrentUser()
        }
    }

    override suspend fun getUser(uid: String): User? {
        return withContext(ioDispatchers) {
            var user: User? = null
            userCollection
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    _currentUser = document.toObject<User>()
                    user = getCurrentUser()
                }
                .addOnFailureListener { Log.d(TAG, it.localizedMessage!!.toString()) }
                .await()
            user
        }
    }

    override suspend fun deleteUser(uid: String) {
        withContext(ioDispatchers) {
            userCollection
                .document(uid)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        }
    }
}
