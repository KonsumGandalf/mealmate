package konsum.gandalf.mealmate.utils.repositories.images

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import konsum.gandalf.mealmate.utils.await
import konsum.gandalf.mealmate.utils.repositories.images.constants.FirebaseReferenceEnum
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(
    private val imageCollection: StorageReference =
        FirebaseStorage.getInstance().reference.child(FirebaseReferenceEnum.PROFILE_IMAGES),
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO
) : IImageRepository {
    private val TAG = "ImageRepositoryImpl"

    override suspend fun uploadImg(uid: String, imgUri: Uri): String {
        return withContext(ioDispatchers) {
            try {
                imageCollection.child((uid)).delete().await()
            } catch (_: Exception) {}
            imageCollection
                .child(uid)
                .putFile(imgUri)
                .addOnSuccessListener { Log.d(TAG, "Image is uploading") }
                .addOnFailureListener { Log.d(TAG, "Image could not be uploaded") }
                .await()
            val link =
                imageCollection
                    .child(uid)
                    .downloadUrl
                    .addOnSuccessListener {}
                    .addOnFailureListener { Log.d(TAG, "Image could not be uploaded") }
                    .await()
                    .toString()
            link
        }
    }
}
