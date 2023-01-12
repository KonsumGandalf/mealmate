package konsum.gandalf.mealmate.authentication.ui.utils

sealed class ToastEvent {
	data class Message(val message : String) : ToastEvent()
	data class ErrorCode(val code : Int): ToastEvent()
	data class Error(val error : String) : ToastEvent()
}