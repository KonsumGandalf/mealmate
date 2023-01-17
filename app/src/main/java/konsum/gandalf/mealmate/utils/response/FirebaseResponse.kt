package konsum.gandalf.mealmate.utils.response

sealed class FirebaseResponse<out R> {
    data class Success<out R>(val result: R) : FirebaseResponse<R>()
    data class Failure<out R>(val exception: R) : FirebaseResponse<Nothing>()
    object Loading : FirebaseResponse<Nothing>()
}
