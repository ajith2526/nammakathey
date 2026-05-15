package com.example.nammakathey.ui.screens.district

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nammakathey.R
import com.example.nammakathey.data.remote.District
import com.example.nammakathey.data.remote.FirebaseService
import com.example.nammakathey.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictDetailScreen(
    districtId: String,
    navController: NavController,
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit
) {
    val district = FirebaseService.getDistrictById(districtId)
    
    if (district == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("District not found")
        }
        return
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEnglish) district.name else district.nameKn) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            // Header Image with Gradient
            Box(modifier = Modifier.height(300.dp)) {
                val bannerRes = getDrawableResource(district.bannerImage)
                Image(
                    painter = painterResource(id = bannerRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    Text(
                        text = if (isEnglish) district.name else district.nameKn,
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isEnglish) district.shortDesc else district.shortDescKn,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Info Sections
            Column(modifier = Modifier.padding(24.dp)) {
                
                DetailSection(
                    icon = Icons.Default.History,
                    title = if (isEnglish) "History" else "ಇತಿಹಾಸ",
                    content = if (isEnglish) district.history else district.historyKn
                )

                Spacer(modifier = Modifier.height(24.dp))

                DetailSection(
                    icon = Icons.Default.LocationOn,
                    title = if (isEnglish) "Famous Landmark" else "ಪ್ರಸಿದ್ಧ ಸ್ಥಳ",
                    content = if (isEnglish) district.famousLandmark else district.famousLandmarkKn,
                    imageRes = getDrawableResource(district.landmarkImage)
                )

                Spacer(modifier = Modifier.height(24.dp))

                DetailSection(
                    icon = Icons.Default.MusicNote,
                    title = if (isEnglish) "Culture & Specialty" else "ಸಂಸ್ಕೃತಿ ಮತ್ತು ವಿಶೇಷತೆ",
                    content = if (isEnglish) district.cultureSpecialty else district.cultureSpecialtyKn
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                district.heroId?.let { heroId ->
                    Button(
                        onClick = { navController.navigate(Screen.Story.createRoute(heroId)) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isEnglish) "Meet the Hero" else "ವೀರರನ್ನು ಭೇಟಿ ಮಾಡಿ", fontSize = 18.sp)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedButton(
                        onClick = { navController.navigate(Screen.Quiz.createRoute(heroId)) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isEnglish) "Take the Quiz" else "ಕ್ವಿಜ್ ತೆಗೆದುಕೊಳ್ಳಿ", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailSection(
    icon: ImageVector,
    title: String,
    content: String,
    imageRes: Int? = null
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(content, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        imageRes?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// Utility to safely map string to drawable resource
fun getDrawableResource(name: String): Int {
    return try {
        R.drawable::class.java.getField(name).getInt(null)
    } catch (e: Exception) {
        R.drawable.ic_launcher_background
    }
}
