package com.nammakathey.app.models

data class Hero(
    val id: String,
    val nameEn: String,
    val nameKn: String,
    val districtId: String,
    val categoryEn: String,
    val categoryKn: String,
    val yearBorn: Int,
    val yearDied: Int,
    val shortBioEn: String,
    val shortBioKn: String,
    val storyPages: List<StoryPage>,
    val quizQuestions: List<QuizQuestion>,
    val statueLat: Double,
    val statueLng: Double,
    val statueDescEn: String,
    val statueDescKn: String
)
