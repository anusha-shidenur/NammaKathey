package com.nammakathey.app.models

data class Badge(
    val heroId: String,
    val heroNameEn: String,
    val heroNameKn: String,
    val districtNameEn: String,
    val districtNameKn: String,
    val earnedAt: Long,
    val score: Int
)
