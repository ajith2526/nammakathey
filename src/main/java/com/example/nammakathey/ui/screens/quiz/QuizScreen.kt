package com.example.nammakathey.ui.screens.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nammakathey.data.repository.DistrictRepository
import com.example.nammakathey.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    districtId: String,
    navController: NavController,
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit
) {
    val district = remember(districtId) { DistrictRepository.getDistrictById(districtId) }
    val questions = district?.hero?.quizQuestions ?: emptyList()
    
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }

    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(if (isEnglish) "No quiz available for this district." else "ಈ ಜಿಲ್ಲೆಗೆ ಯಾವುದೇ ರಸಪ್ರಶ್ನೆ ಲಭ್ಯವಿಲ್ಲ.")
        }
        return
    }

    val currentQuestion = questions[currentQuestionIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEnglish) "Quiz Time!" else "ರಸಪ್ರಶ್ನೆ ಸಮಯ!") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = if (isEnglish) currentQuestion.question else currentQuestion.kannadaQuestion,
                    modifier = Modifier.padding(24.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Options
            Column(modifier = Modifier.selectableGroup()) {
                val options = if (isEnglish) currentQuestion.options else currentQuestion.kannadaOptions
                options.forEachIndexed { index, option ->
                    QuizOptionRow(
                        text = option,
                        isSelected = selectedOptionIndex == index,
                        isCorrect = index == currentQuestion.correctOptionIndex,
                        isAnswered = isAnswered,
                        onClick = {
                            if (!isAnswered) {
                                selectedOptionIndex = index
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Button
            Button(
                onClick = {
                    if (!isAnswered) {
                        if (selectedOptionIndex != null) {
                            isAnswered = true
                            if (selectedOptionIndex == currentQuestion.correctOptionIndex) {
                                score++
                            }
                        }
                    } else {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedOptionIndex = null
                            isAnswered = false
                        } else {
                            navController.navigate(Screen.Result.createRoute(score, questions.size, districtId)) {
                                popUpTo(Screen.Quiz.route) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = selectedOptionIndex != null,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (!isAnswered) {
                        if (isEnglish) "Check Answer" else "ಸರಿ ಉತ್ತರ ಪರೀಕ್ಷಿಸಿ"
                    } else {
                        if (currentQuestionIndex < questions.size - 1) {
                            if (isEnglish) "Next Question" else "ಮುಂದಿನ ಪ್ರಶ್ನೆ"
                        } else {
                            if (isEnglish) "Finish" else "ಮುಕ್ತಾಯ"
                        }
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun QuizOptionRow(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isAnswered && isCorrect -> Color(0xFFC8E6C9) // Light Green
        isAnswered && isSelected && !isCorrect -> Color(0xFFFFCDD2) // Light Red
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val borderColor = when {
        isAnswered && isCorrect -> Color.Green
        isAnswered && isSelected && !isCorrect -> Color.Red
        isSelected -> MaterialTheme.colorScheme.primary
        else -> Color.LightGray
    }

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, borderColor),
        color = backgroundColor,
        tonalElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            
            if (isAnswered) {
                if (isCorrect) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = Color.Green)
                } else if (isSelected) {
                    Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = Color.Red)
                }
            }
        }
    }
}
