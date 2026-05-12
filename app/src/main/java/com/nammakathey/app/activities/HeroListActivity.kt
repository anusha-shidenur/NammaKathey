package com.nammakathey.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammakathey.app.R
import com.nammakathey.app.adapters.HeroAdapter
import com.nammakathey.app.databinding.ActivityHeroListBinding
import com.nammakathey.app.models.District
import com.nammakathey.app.utils.DataManager
import com.nammakathey.app.utils.PrefsManager

class HeroListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DISTRICT_ID = "district_id"
    }

    private lateinit var binding: ActivityHeroListBinding
    private lateinit var prefs: PrefsManager
    private var district: District? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        val districtId = intent.getStringExtra(EXTRA_DISTRICT_ID) ?: run { finish(); return }
        district = DataManager.getDistrictById(this, districtId)

        setupToolbar()
        setupHeroList()
        animateEntrance()
    }

    private fun setupToolbar() {
        val d = district ?: return
        val isKn = prefs.isKannada
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isKn) d.nameKn else d.nameEn
        binding.tvDistrictDesc.text = if (isKn) d.descriptionKn else d.descriptionEn
        binding.tvHeroCount.text = "${d.heroes.size} ${if (isKn) "ವೀರರು" else "Heroes"}"
    }

    private fun setupHeroList() {
        val d = district ?: return
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.adapter = HeroAdapter(d.heroes, prefs) { hero ->
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra(StoryActivity.EXTRA_HERO_ID, hero.id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun animateEntrance() {
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.rvHeroes.startAnimation(slideIn)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
