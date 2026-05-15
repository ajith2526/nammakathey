package com.example.nammakathey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nammakathey.ui.screens.home.HomeScreen
import com.example.nammakathey.ui.screens.quiz.QuizScreen
import com.example.nammakathey.ui.screens.quiz.ResultScreen
import com.example.nammakathey.ui.screens.story.StoryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                isEnglish = isEnglish,
                onLanguageToggle = onLanguageToggle
            )
        }

        composable(
            route = Screen.Story.route,
            arguments = listOf(
                navArgument("districtId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val districtId = backStackEntry.arguments?.getString("districtId") ?: ""
            StoryScreen(
                districtId = districtId,
                navController = navController,
                isEnglish = isEnglish,
                onLanguageToggle = onLanguageToggle
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("districtId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val districtId = backStackEntry.arguments?.getString("districtId") ?: ""
            QuizScreen(
                districtId = districtId,
                navController = navController,
                isEnglish = isEnglish,
                onLanguageToggle = onLanguageToggle
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("districtId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val districtId = backStackEntry.arguments?.getString("districtId") ?: ""
            ResultScreen(
                score = score,
                total = total,
                districtId = districtId,
                navController = navController,
                isEnglish = isEnglish
            )
        }
    }
}
