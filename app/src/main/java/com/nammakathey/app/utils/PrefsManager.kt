package com.nammakathey.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nammakathey.app.models.Badge

class PrefsManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("namma_kathey_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        const val KEY_LANGUAGE = "language"
        const val KEY_BADGES = "badges"
        const val LANG_EN = "en"
        const val LANG_KN = "kn"
    }

    var language: String
        get() = prefs.getString(KEY_LANGUAGE, LANG_EN) ?: LANG_EN
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value).apply()

    val isKannada: Boolean get() = language == LANG_KN

    fun getBadges(): List<Badge> {
        val json = prefs.getString(KEY_BADGES, null) ?: return emptyList()
        val type = object : TypeToken<List<Badge>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun hasBadge(heroId: String): Boolean {
        return getBadges().any { it.heroId == heroId }
    }

    fun saveBadge(badge: Badge) {
        val badges = getBadges().toMutableList()
        if (!badges.any { it.heroId == badge.heroId }) {
            badges.add(badge)
            prefs.edit().putString(KEY_BADGES, gson.toJson(badges)).apply()
        }
    }

    fun getBadgeCount(): Int = getBadges().size

    fun toggleLanguage() {
        language = if (isKannada) LANG_EN else LANG_KN
    }
}
