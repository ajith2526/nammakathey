package com.example.nammakathey.ui.screens.story

import android.speech.tts.TextToSpeech
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nammakathey.data.models.StoryPage
import com.example.nammakathey.data.repository.DistrictRepository
import com.example.nammakathey.ui.navigation.Screen
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryScreen(
    districtId: String,
    navController: NavController,
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit
) {
    val district = remember(districtId) { DistrictRepository.getDistrictById(districtId) }
    val hero = district?.hero ?: return

    val pagerState = rememberPagerState(pageCount = { hero.historyPages.size })
    val context = LocalContext.current
    
    // TTS Initialization
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(Unit) {
        val speech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Initialized
            }
        }
        tts = speech
        onDispose {
            speech.stop()
            speech.shutdown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEnglish) hero.name else hero.kannadaName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val textToRead = if (isEnglish) {
                            hero.historyPages[pagerState.currentPage].content
                        } else {
                            hero.historyPages[pagerState.currentPage].kannadaContent
                        }
                        tts?.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
                    }) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Read Aloud")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                StoryPageItem(hero.historyPages[pageIndex], isEnglish)
            }

            // Pager Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(hero.historyPages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            // Navigation Button
            Button(
                onClick = {
                    if (pagerState.currentPage < hero.historyPages.size - 1) {
                        // Logic to scroll to next page if needed
                    } else {
                        navController.navigate(Screen.Quiz.createRoute(districtId))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage < hero.historyPages.size - 1) {
                        if (isEnglish) "Next Page" else "ಮುಂದಿನ ಪುಟ"
                    } else {
                        if (isEnglish) "Take Quiz" else "ರಸಪ್ರಶ್ನೆ ಪ್ರಾರಂಭಿಸಿ"
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StoryPageItem(page: StoryPage, isEnglish: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            AsyncImage(
                model = page.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isEnglish) page.title else page.kannadaTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEnglish) page.content else page.kannadaContent,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
