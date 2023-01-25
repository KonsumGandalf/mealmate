package konsum.gandalf.mealmate.utils.repositories.images

import android.net.Uri

interface IImageRepository {
    suspend fun uploadImg(uid: String, imgUri: Uri): String?
}
