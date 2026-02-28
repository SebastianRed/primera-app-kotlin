package cl.sebastianrojo.primera_app_kotlin.trivia

data class Question(
    val id: Int,
    val title: String,
    val options: List<String>,
    val correctIndex: Int
)

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val feedbackState: FeedbackState = FeedbackState.None,
    val lives: Int = 3
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)
}

sealed class FeedbackState {
    object None : FeedbackState()
    object Correct : FeedbackState()
    object Incorrect : FeedbackState()
}