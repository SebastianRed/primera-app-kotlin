package cl.sebastianrojo.primera_app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.sebastianrojo.primera_app_kotlin.trivia.FeedbackState
import cl.sebastianrojo.primera_app_kotlin.trivia.QuizUiState
import cl.sebastianrojo.primera_app_kotlin.trivia.QuizViewModel
import cl.sebastianrojo.primera_app_kotlin.ui.theme.AppKotlinTheme

class TriviaAppActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppKotlinTheme {
                val viewModel: QuizViewModel = viewModel()
                val state = viewModel.uiState.collectAsStateWithLifecycle().value

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Trivia App",
                                    color = Color.White
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Volver",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF1E88E5)
                            )
                        )
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if (state.isFinished) {
                            FinishedScreen(
                                score = state.score,
                                total = state.questions.size * 100
                            )
                        } else {
                            QuestionScreen(
                                state = state,
                                onSelectedOption = viewModel::onSelectedOption,
                                onConfirm = viewModel::onConfirmAnswer,
                                onNext = viewModel::onNextQuestion
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionScreen(
    state: QuizUiState,
    onSelectedOption: (Int) -> Unit,
    onConfirm: () -> Unit,
    onNext: () -> Unit,
) {
    val q = state.currentQuestion ?: return
    val isLastQuestion = state.currentIndex == state.questions.size - 1
    val hasFeedback = state.feedbackState != FeedbackState.None
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Pregunta ${state.currentIndex + 1} de ${state.questions.size}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = q.title, style = MaterialTheme.typography.headlineSmall)

        q.options.forEachIndexed { index, option ->
            val isSelected = state.selectedIndex == index
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !hasFeedback) { onSelectedOption(index) },
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = if (isSelected) 14.dp else 1.dp
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { if (!hasFeedback) onSelectedOption(index) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = option, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        if (hasFeedback) {
            val (emoji, msg, color) = when (state.feedbackState) {
                is FeedbackState.Correct -> Triple("✅", "Correcto", Color(0xFF388E3C))
                is FeedbackState.Incorrect -> Triple("❌", "Incorrecto", Color(0xFFC62828))
                else -> Triple("", "", Color.Transparent)
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "$emoji $msg",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
            }
        }
        Button(
            onClick = if (hasFeedback) onNext else onConfirm,
            enabled = state.selectedIndex != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                when {
                    !hasFeedback -> "Confirmar"
                    isLastQuestion -> "Ver resultados"
                    else -> "Siguiente"
                }
            )
        }
    }
}

@Composable
fun FinishedScreen(
    score: Int,
    total: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Quiz finalizado!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.height(48.dp)
        )
        Text(
            text = "Tu puntaje: $score / $total",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier.height(64.dp)
        )
        Button(
            onClick = {}
        ) {
            Text("Reintentar Quiz")
        }
    }
}