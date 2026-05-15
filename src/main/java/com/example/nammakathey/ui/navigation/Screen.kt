package com.example.nammakathey.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home_screen")

    object DistrictDetail : Screen("district_detail/{districtId}") {
        fun createRoute(districtId: String): String {
            return "district_detail/$districtId"
        }
    }

    object Story : Screen("story_screen/{districtId}") {
        fun createRoute(districtId: String): String {
            return "story_screen/$districtId"
        }
    }

    object Quiz : Screen("quiz_screen/{districtId}") {
        fun createRoute(districtId: String): String {
            return "quiz_screen/$districtId"
        }
    }

    object Result : Screen("result_screen/{score}/{total}/{districtId}") {
        fun createRoute(score: Int, total: Int, districtId: String): String {
            return "result_screen/$score/$total/$districtId"
        }
    }
}