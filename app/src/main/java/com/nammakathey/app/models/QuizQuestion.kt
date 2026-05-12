package com.nammakathey.app.models

data class QuizQuestion(
    val questionEn: String,
    val questionKn: String,
    val optionsEn: List<String>,
    val optionsKn: List<String>,
    val correctIndex: Int,
    val explanationEn: String,
    val explanationKn: String
)
