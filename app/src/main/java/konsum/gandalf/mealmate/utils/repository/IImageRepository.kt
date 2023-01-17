package konsum.gandalf.mealmate.utils.repository

import android.net.Uri

interface IImageRepository {

    // this is an interface that will implement all common authentication
    // functions. That is sign in  , sign up , logout. Taking this approach will allow us
    // to rely on abstractions rather than a concrete authentication repository class. This
    // will make it easy for us to test and maintain in that whatever form of repository class
    // we will use it won't matter since all will inherit from this class. So swapping
    // of repositories will be easy.

    suspend fun uploadImg(uid: String, imgUri: Uri): String?
}
