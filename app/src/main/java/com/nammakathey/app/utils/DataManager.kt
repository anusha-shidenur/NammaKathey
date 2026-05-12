package com.nammakathey.app.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.nammakathey.app.R
import com.nammakathey.app.models.District
import com.nammakathey.app.models.Hero

object DataManager {

    private var districts: List<District>? = null

    fun loadData(context: Context): List<District> {
        if (districts != null) return districts!!
        return try {
            val inputStream = context.resources.openRawResource(R.raw.heroes_data)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
            val type = object : TypeToken<List<District>>() {}.type
            val loaded: List<District> = gson.fromJson(jsonObject.getAsJsonArray("districts"), type)
            districts = loaded
            loaded
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getDistrictById(context: Context, districtId: String): District? {
        return loadData(context).find { it.id == districtId }
    }

    fun getHeroById(context: Context, heroId: String): Hero? {
        return loadData(context).flatMap { it.heroes }.find { it.id == heroId }
    }

    fun getAllHeroes(context: Context): List<Hero> {
        return loadData(context).flatMap { it.heroes }
    }
}
