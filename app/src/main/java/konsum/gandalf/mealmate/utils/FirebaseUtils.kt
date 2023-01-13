package konsum.gandalf.mealmate.authentication.repositories.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
	return suspendCancellableCoroutine { content ->
		addOnCompleteListener {
			if (it.exception != null) {
				content.resumeWithException(it.exception!!)
			} else {
				content.resume(it.result, null)
			}
		}
	}
}
