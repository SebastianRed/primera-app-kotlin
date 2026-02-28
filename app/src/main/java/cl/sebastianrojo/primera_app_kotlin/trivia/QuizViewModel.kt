package cl.sebastianrojo.primera_app_kotlin.trivia

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
        val newLives = if (isCorrect) current.lives else current.lives - 1  // 👈 Resta vida si falla

        _uiState.value = current.copy(
            score = newScore,
            lives = newLives,
            feedbackState = if (isCorrect) FeedbackState.Correct else FeedbackState.Incorrect
        )
    }

    fun onNextQuestion() {
        val current = _uiState.value
        val nextIndex = current.currentIndex + 1
        // 👇 Finaliza si se acaban las preguntas O si no quedan vidas
        val finished = nextIndex >= current.questions.size || current.lives <= 0

        _uiState.value = current.copy(
            currentIndex = nextIndex,
            selectedIndex = null,
            feedbackState = FeedbackState.None,
            isFinished = finished
        )
    }

    fun onRetry() {
        _uiState.value = QuizUiState(
            questions = seedQuestions()
        )
    }

    private fun seedQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Qué palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿qué anotación marca una función como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Qué componente se usa para listas eficientes y scrolleables?",
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
            ),
            Question(
                id = 7,
                title = "¿Qué modificador en Compose hace que un elemento ocupe todo el ancho disponible?",
                options = listOf("fillMaxHeight()", "wrapContentWidth()", "fillMaxWidth()", "matchParentWidth()"),
                correctIndex = 2
            ),
            Question(
                id = 8,
                title = "¿Qué clase se usa en Kotlin para representar un resultado que puede ser éxito o fallo?",
                options = listOf("Optional", "Result", "Either", "Maybe"),
                correctIndex = 1
            ),
            Question(
                id = 9,
                title = "¿Cuál es la forma correcta de iterar una lista en Kotlin?",
                options = listOf("list.each { }", "for (item in list) { }", "list.loop { }", "foreach item in list { }"),
                correctIndex = 1
            ),
            Question(
                id = 10,
                title = "¿Qué es un StateFlow en Kotlin?",
                options = listOf("Un tipo de lista observable", "Un flujo de datos con estado actual siempre disponible", "Una corrutina suspendida", "Un canal de comunicación entre Activities"),
                correctIndex = 1
            ),
            Question(
                id = 11,
                title = "¿Qué función de Compose se usa para recordar un valor entre recomposiciones?",
                options = listOf("rememberSaveable", "remember", "mutableStateOf", "collectAsState"),
                correctIndex = 1
            ),
            Question(
                id = 12,
                title = "¿Qué palabra clave en Kotlin permite una función ser suspendida?",
                options = listOf("async", "suspend", "await", "coroutine"),
                correctIndex = 1
            ),
            Question(
                id = 13,
                title = "¿Qué componente de Compose muestra texto editable por el usuario?",
                options = listOf("Text", "Label", "TextField", "InputField"),
                correctIndex = 2
            ),
            Question(
                id = 14,
                title = "¿Qué operador en Kotlin lanza excepción si un valor nullable es null?",
                options = listOf("?.", "?:", "!!", "::"),
                correctIndex = 2
            ),
            Question(
                id = 15,
                title = "¿Qué patrón de arquitectura recomienda Google para apps Android modernas?",
                options = listOf("MVC", "MVP", "MVVM con UiState", "Singleton"),
                correctIndex = 2
            )
        )
    }
}