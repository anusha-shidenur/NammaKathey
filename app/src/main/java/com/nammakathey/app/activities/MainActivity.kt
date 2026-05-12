package com.nammakathey.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nammakathey.app.R
import com.nammakathey.app.adapters.DistrictAdapter
import com.nammakathey.app.databinding.ActivityMainBinding
import com.nammakathey.app.models.District
import com.nammakathey.app.utils.DataManager
import com.nammakathey.app.utils.PrefsManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: PrefsManager
    private lateinit var districtAdapter: DistrictAdapter
    private var districts: List<District> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        setupUI()
        loadDistricts()
        setupClickListeners()
        animateEntrance()
    }

    override fun onResume() {
        super.onResume()
        updateBadgeCount()
        updateLanguageUI()
    }

    private fun setupUI() {
        updateLanguageUI()
        binding.rvDistricts.layoutManager = GridLayoutManager(this, 2)
    }

    private fun updateLanguageUI() {
        val isKn = prefs.isKannada
        binding.tvWelcome.text = if (isKn) getString(R.string.welcome_text_kn) else getString(R.string.welcome_text)
        binding.tvSelectDistrict.text = if (isKn) getString(R.string.select_district_kn) else getString(R.string.select_district)
        binding.btnLanguage.text = if (isKn) "English" else "ಕನ್ನಡ"
        updateBadgeCount()
    }

    private fun updateBadgeCount() {
        val count = prefs.getBadgeCount()
        binding.tvBadgeCount.text = "$count"
        binding.badgeCountContainer.visibility = if (count > 0) View.VISIBLE else View.GONE
    }

    private fun loadDistricts() {
        districts = DataManager.loadData(this)
        districtAdapter = DistrictAdapter(districts, prefs) { district ->
            val intent = Intent(this, HeroListActivity::class.java)
            intent.putExtra(HeroListActivity.EXTRA_DISTRICT_ID, district.id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        binding.rvDistricts.adapter = districtAdapter
    }

    private fun setupClickListeners() {
        binding.btnLanguage.setOnClickListener {
            prefs.toggleLanguage()
            updateLanguageUI()
            districtAdapter.notifyDataSetChanged()
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.badgeCountContainer.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun animateEntrance() {
        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        binding.headerLayout.startAnimation(slideDown)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.rvDistricts.startAnimation(fadeIn)
    }
}
