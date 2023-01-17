package konsum.gandalf.mealmate.utils.events

sealed class CustomEvent {
    data class Message(val message: String) : CustomEvent()
    data class ErrorCode(val code: Int) : CustomEvent()
    data class Error(val error: String) : CustomEvent()
}
