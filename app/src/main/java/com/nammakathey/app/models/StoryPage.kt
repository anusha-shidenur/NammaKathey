package com.nammakathey.app.models

data class StoryPage(
    val pageNumber: Int,
    val titleEn: String,
    val titleKn: String,
    val contentEn: String,
    val contentKn: String,
    val illustrationEmoji: String,
    val imageUrl: String = "" // Wikipedia image URL
)
