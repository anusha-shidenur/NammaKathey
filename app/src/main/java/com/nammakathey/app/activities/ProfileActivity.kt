package com.nammakathey.app.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nammakathey.app.R
import com.nammakathey.app.adapters.BadgeAdapter
import com.nammakathey.app.databinding.ActivityProfileBinding
import com.nammakathey.app.utils.PrefsManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var prefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        val isKn = prefs.isKannada
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isKn) "ನನ್ನ ಪ್ರೊಫೈಲ್" else getString(R.string.profile_title)

        setupBadges()
        animateEntrance()
    }

    private fun setupBadges() {
        val badges = prefs.getBadges()
        val isKn = prefs.isKannada

        binding.tvBadgesEarned.text = "${badges.size} ${if (isKn) "ಬ್ಯಾಡ್ಜ್‌ಗಳು" else "Badges Earned"}"
        binding.tvProgressMessage.text = if (badges.isEmpty()) {
            if (isKn) "ಇನ್ನೂ ಬ್ಯಾಡ್ಜ್ ಇಲ್ಲ. ರಸಪ್ರಶ್ನೆ ಮಾಡಿ ಬ್ಯಾಡ್ಜ್ ಗಳಿಸಿ!" else getString(R.string.no_badges)
        } else {
            val total = 8
            if (isKn) "${badges.size}/$total ಪರಂಪರೆ ಬ್ಯಾಡ್ಜ್‌ಗಳನ್ನು ಗಳಿಸಿದ್ದೀರಿ! 🏆" else "${badges.size}/$total Heritage Badges earned! 🏆"
        }

        if (badges.isEmpty()) {
            binding.rvBadges.visibility = View.GONE
            binding.tvNoBadges.visibility = View.VISIBLE
            binding.tvNoBadges.text = if (isKn) "ಇನ್ನೂ ಬ್ಯಾಡ್ಜ್ ಇಲ್ಲ" else "No badges yet"
        } else {
            binding.rvBadges.visibility = View.VISIBLE
            binding.tvNoBadges.visibility = View.GONE
            binding.rvBadges.layoutManager = GridLayoutManager(this, 2)
            binding.rvBadges.adapter = BadgeAdapter(badges, prefs)
        }
    }

    private fun animateEntrance() {
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.rvBadges.startAnimation(slideIn)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.headerCard.startAnimation(fadeIn)
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
