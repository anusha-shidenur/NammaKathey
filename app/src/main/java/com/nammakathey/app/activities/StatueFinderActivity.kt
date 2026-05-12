package com.nammakathey.app.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nammakathey.app.R
import com.nammakathey.app.databinding.ActivityStatueFinderBinding
import com.nammakathey.app.models.Hero
import com.nammakathey.app.utils.DataManager
import com.nammakathey.app.utils.PrefsManager

class StatueFinderActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HERO_ID = "hero_id"
        const val LOCATION_PERMISSION_REQUEST = 100
    }

    private lateinit var binding: ActivityStatueFinderBinding
    private lateinit var prefs: PrefsManager
    private var hero: Hero? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatueFinderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        val heroId = intent.getStringExtra(EXTRA_HERO_ID) ?: run { finish(); return }
        hero = DataManager.getHeroById(this, heroId)

        val isKn = prefs.isKannada
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isKn) "ಸ್ಮಾರಕ ಹುಡುಕಿ" else getString(R.string.statue_finder_title)

        setupHeroInfo()
        setupButtons()
    }

    private fun setupHeroInfo() {
        val h = hero ?: return
        val isKn = prefs.isKannada
        binding.tvHeroName.text = if (isKn) h.nameKn else h.nameEn
        binding.tvStatueDescription.text = if (isKn) h.statueDescKn else h.statueDescEn
        binding.tvCoordinates.text = "📍 ${String.format("%.4f", h.statueLat)}, ${String.format("%.4f", h.statueLng)}"
        binding.tvHeroEmoji.text = when (h.categoryEn) {
            "Freedom Fighter", "Queen & Freedom Fighter", "Sea Queen & Freedom Fighter" -> "⚔️"
            "Social Reformer & Philosopher", "Poet Saint & Social Reformer" -> "🪔"
            "Emperor & Poet" -> "👑"
            "Founder & Builder" -> "🏗️"
            "Poet & Social Activist" -> "✍️"
            else -> "⭐"
        }
    }

    private fun setupButtons() {
        val h = hero ?: return
        val isKn = prefs.isKannada

        binding.btnOpenMaps.setOnClickListener {
            val uri = Uri.parse("geo:${h.statueLat},${h.statueLng}?q=${h.statueLat},${h.statueLng}(${if (isKn) h.nameKn else h.nameEn})")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                val browserUri = Uri.parse("https://maps.google.com/?q=${h.statueLat},${h.statueLng}")
                startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        }

        binding.btnDirections.setOnClickListener {
            val uri = Uri.parse("google.navigation:q=${h.statueLat},${h.statueLng}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                val browserUri = Uri.parse("https://maps.google.com/maps?daddr=${h.statueLat},${h.statueLng}")
                startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { onBackPressedDispatcher.onBackPressed(); return true }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
