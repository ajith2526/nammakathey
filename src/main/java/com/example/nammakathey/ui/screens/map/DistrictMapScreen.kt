package com.example.nammakathey.ui.screens.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nammakathey.R
import com.example.nammakathey.data.remote.District
import com.example.nammakathey.data.remote.FirebaseService
import com.example.nammakathey.ui.components.TopBar
import com.example.nammakathey.ui.navigation.Screen

@Composable
fun DistrictMapScreen(
    navController: NavController,
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit
) {
    val districts = FirebaseService.getDistricts()
    val heroes = FirebaseService.getHeroes()
    val categories = listOf("Heritage", "Coastal", "Malnad", "Bayaluseeme")
    
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = if (isEnglish) "Explore Karnataka" else "ಕರ್ನಾಟಕ ಅನ್ವೇಷಿಸಿ",
                isEnglish = isEnglish,
                onLanguageToggle = onLanguageToggle
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Hero / Search Section
            item {
                SearchBarSection(searchQuery, onQueryChange = { searchQuery = it }, isEnglish)
            }

            // Categories Section
            item {
                CategorySection(categories, isEnglish)
            }

            // Featured Districts (Horizontal Scroll)
            item {
                SectionHeader(if (isEnglish) "Featured" else "ವೈಶಿಷ್ಟ್ಯಗಳು", isEnglish)
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(districts.take(3)) { district ->
                        FeaturedDistrictCard(district, isEnglish) {
                            val hero = heroes.find { it.districtId == district.id }
                            hero?.let { navController.navigate(Screen.Story.createRoute(it.id)) }
                        }
                    }
                }
            }

            // All Districts by Category
            categories.forEach { category ->
                val filteredDistricts = districts.filter { it.category == category }
                if (filteredDistricts.isNotEmpty()) {
                    item {
                        SectionHeader(category, isEnglish)
                    }
                    items(filteredDistricts.chunked(2)) { pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            pair.forEach { district ->
                                CompactDistrictCard(
                                    district = district,
                                    isEnglish = isEnglish,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    val hero = heroes.find { it.districtId == district.id }
                                    hero?.let { navController.navigate(Screen.Story.createRoute(it.id)) }
                                }
                            }
                            if (pair.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun SearchBarSection(query: String, onQueryChange: (String) -> Unit, isEnglish: Boolean) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(if (isEnglish) "Search districts, heroes..." else "ಜಿಲ್ಲೆಗಳು, ವೀರರನ್ನು ಹುಡುಕಿ...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun CategorySection(categories: List<String>, isEnglish: Boolean) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = false,
                onClick = { },
                label = { Text(category) },
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, isEnglish: Boolean) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun FeaturedDistrictCard(district: District, isEnglish: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            // Placeholder Image mapping
            val imageRes = when(district.id) {
                "mysuru" -> R.drawable.mysuru_palace
                "ballari" -> R.drawable.hampi_ruins
                "shivamogga" -> R.drawable.jog_falls
                else -> R.drawable.ic_launcher_background
            }
            
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = if (isEnglish) district.name else district.nameKn,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isEnglish) district.shortDesc else district.shortDescKn,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun CompactDistrictCard(district: District, isEnglish: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isEnglish) district.name else district.nameKn,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = if (isEnglish) district.category else district.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEnglish) "Explore →" else "ಅನ್ವೇಷಿಸಿ →",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
