package com.example.nammakathey.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, isEnglish: Boolean, onLanguageToggle: () -> Unit) {
    TopAppBar(
        title = { 
            Text(
                text = title, 
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            ) 
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = if (isEnglish) "EN" else "ಕನ್ನಡ",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                IconButton(onClick = onLanguageToggle) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Toggle Language",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}