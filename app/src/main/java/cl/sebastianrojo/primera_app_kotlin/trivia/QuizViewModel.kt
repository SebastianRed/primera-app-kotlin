package cl.sebastianrojo.primera_app_kotlin.trivia

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = seedQuestions()
        )
    )

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onSelectedOption(index: Int) {
        val current = _uiState.value

        if (current.isFinished) return

        _uiState.value = current.copy(selectedIndex = index)
    }

    fun onConfirmAnswer() {
        val current = _uiState.value
        val selected = current.selectedIndex ?: return
        val currentQuestion = current.currentQuestion ?: return

        val isCorrect = selected == currentQuestion.correctIndex
        val newScore = if (isCorrect) current.score + 100 else current.score

        _uiState.value = current.copy(
            score = newScore,
            feedbackState = if (isCorrect) FeedbackState.Correct else FeedbackState.Incorrect
        )
    }

    fun onNextQuestion() {
        val current = _uiState.value
        val nextIndex = current.currentIndex + 1
        val finished = nextIndex >= current.questions.size

        _uiState.value = current.copy(
            currentIndex = nextIndex,
            selectedIndex = null,
            feedbackState = FeedbackState.None,
            isFinished = finished
        )
    }

    private fun seedQuestions() : List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Que palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿que anotacion marca una funcion como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Que componente se usa para listas eficientes y scrolleables?",
                options = listOf("Column", "RecyclerView", "Stack", "LazyColumn"),
                correctIndex = 3
            ),
            Question(
                id = 4,
                title = "La instrucción que permite restaurar estado tras recreación de Activity es",
                options = listOf("intentData", "savedInstanceState", "activityState", "bundleConfig"),
                correctIndex = 1
            ),
            Question(
                id = 5,
                title = "¿Qué función se usa para manejar efectos secundarios en Compose?",
                options = listOf("remember", "LaunchedEffect", "derivedStateOf", "mutableStateOf"),
                correctIndex = 1
            ),
            Question(
                id = 6,
                title = "¿Cuál es la clase base de un ViewModel en Android?",
                options = listOf("BaseViewModel", "AndroidViewModel", "ViewModel", "LiveDataModel"),
                correctIndex = 2
            )
        )
    }
}