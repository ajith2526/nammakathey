package com.example.nammakathey.data.models

data class District(
    val id: String,
    val name: String,
    val kannadaName: String,
    val imageUrl: String,
    val color: Long,
    val hero: Hero
)

data class Hero(
    val name: String,
    val kannadaName: String,
    val imageUrl: String,
    val location: String,
    val kannadaLocation: String,
    val historyPages: List<StoryPage>,
    val quizQuestions: List<QuizQuestion>
)

data class StoryPage(
    val title: String,
    val kannadaTitle: String,
    val content: String,
    val kannadaContent: String,
    val imageUrl: String
)

data class QuizQuestion(
    val question: String,
    val kannadaQuestion: String,
    val options: List<String>,
    val kannadaOptions: List<String>,
    val correctOptionIndex: Int
)
