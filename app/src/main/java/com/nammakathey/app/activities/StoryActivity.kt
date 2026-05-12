package com.nammakathey.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nammakathey.app.R
import com.nammakathey.app.adapters.StoryPageAdapter
import com.nammakathey.app.databinding.ActivityStoryBinding
import com.nammakathey.app.models.Hero
import com.nammakathey.app.utils.DataManager
import com.nammakathey.app.utils.PrefsManager
import com.nammakathey.app.utils.TTSManager

class StoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HERO_ID = "hero_id"
    }

    private lateinit var binding: ActivityStoryBinding
    private lateinit var prefs: PrefsManager
    private lateinit var tts: TTSManager
    private var hero: Hero? = null
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)
        tts = TTSManager(this)

        val heroId = intent.getStringExtra(EXTRA_HERO_ID) ?: run { finish(); return }
        hero = DataManager.getHeroById(this, heroId)

        setupToolbar()
        setupStoryPager()
        setupButtons()
        updateNavigationButtons()
    }

    private fun setupToolbar() {
        val h = hero ?: return
        val isKn = prefs.isKannada
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isKn) h.nameKn else h.nameEn
        binding.tvCategory.text = if (isKn) h.categoryKn else h.categoryEn
        binding.tvYears.text = "${h.yearBorn} – ${h.yearDied}"
        binding.tvShortBio.text = if (isKn) h.shortBioKn else h.shortBioEn
        if (prefs.hasBadge(h.id)) {
            binding.badgeEarned.visibility = View.VISIBLE
        }
    }

    private fun setupStoryPager() {
        val h = hero ?: return
        val adapter = StoryPageAdapter(h.storyPages, prefs)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                updateNavigationButtons()
                if (tts.isSpeaking()) {
                    tts.stop()
                    binding.btnListen.text = if (prefs.isKannada) "ಕೇಳಿ" else getString(R.string.listen_btn)
                }
            }
        })
    }

    private fun setupButtons() {
        val h = hero ?: return
        val isKn = prefs.isKannada

        binding.btnListen.setOnClickListener {
            val page = h.storyPages.getOrNull(currentPage) ?: return@setOnClickListener
            if (tts.isSpeaking()) {
                tts.stop()
                binding.btnListen.text = if (isKn) "ಕೇಳಿ" else getString(R.string.listen_btn)
            } else {
                val text = if (isKn) "${page.titleKn}. ${page.contentKn}" else "${page.titleEn}. ${page.contentEn}"
                tts.speak(text, isKn)
                binding.btnListen.text = if (isKn) "ನಿಲ್ಲಿಸಿ" else getString(R.string.stop_btn)
                tts.setOnDoneListener {
                    runOnUiThread {
                        binding.btnListen.text = if (isKn) "ಕೇಳಿ" else getString(R.string.listen_btn)
                    }
                }
            }
        }

        binding.btnPrev.setOnClickListener {
            if (currentPage > 0) binding.viewPager.currentItem = currentPage - 1
        }

        binding.btnNext.setOnClickListener {
            if (currentPage < h.storyPages.size - 1) {
                binding.viewPager.currentItem = currentPage + 1
            }
        }

        binding.btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra(QuizActivity.EXTRA_HERO_ID, h.id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnFindStatue.setOnClickListener {
            val intent = Intent(this, StatueFinderActivity::class.java)
            intent.putExtra(StatueFinderActivity.EXTRA_HERO_ID, h.id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun updateNavigationButtons() {
        val pages = hero?.storyPages?.size ?: 0
        binding.btnPrev.isEnabled = currentPage > 0
        binding.btnPrev.alpha = if (currentPage > 0) 1f else 0.4f
        binding.btnNext.isEnabled = currentPage < pages - 1
        binding.btnNext.alpha = if (currentPage < pages - 1) 1f else 0.4f
        binding.tvPageCounter.text = "${currentPage + 1} / $pages"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { onBackPressedDispatcher.onBackPressed(); return true }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
