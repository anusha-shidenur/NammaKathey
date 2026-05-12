package com.nammakathey.app.models

data class District(
    val id: String,
    val nameEn: String,
    val nameKn: String,
    val descriptionEn: String,
    val descriptionKn: String,
    val colorIndex: Int,
    val heroes: List<Hero>
)
