package com.example.nammakathey.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nammakathey.ui.navigation.Screen

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    districtId: String,
    navController: NavController,
    isEnglish: Boolean
) {
    val percentage = (score.toFloat() / total.toFloat()) * 100
    val message = when {
        percentage >= 80 -> if (isEnglish) "Excellent! You're a true patriot!" else "ಅತ್ಯುತ್ತಮ! ನೀವು ನಿಜವಾದ ದೇಶಭಕ್ತರು!"
        percentage >= 50 -> if (isEnglish) "Good Job! Keep learning!" else "ಒಳ್ಳೆಯ ಕೆಲಸ! ಕಲಿಯುವುದನ್ನು ಮುಂದುವರಿಸಿ!"
        else -> if (isEnglish) "Keep Trying! You can do it!" else "ಪ್ರಯತ್ನಿಸುತ್ತಿರಿ! ನೀವು ಇದನ್ನು ಮಾಡಬಹುದು!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isEnglish) "Quiz Result" else "ರಸಪ್ರಶ್ನೆ ಫಲಿತಾಂಶ",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$score / $total",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = if (isEnglish) "Score" else "ಅಂಕಗಳು",
                    fontSize = 20.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = message,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(64.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.Quiz.createRoute(districtId)) {
                        popUpTo(Screen.Result.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isEnglish) "Retry" else "ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ")
            }

            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isEnglish) "Home" else "ಮನೆಗೆ")
            }
        }
    }
}
