package com.nammakathey.app.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.nammakathey.app.R
import com.nammakathey.app.databinding.ActivitySplashBinding
import com.nammakathey.app.utils.DataManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Preload data in background
        Thread { DataManager.loadData(this) }.start()

        // Animate logo
        val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_in)
        val fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.ivLogo.startAnimation(scaleAnim)
        binding.tvAppName.startAnimation(fadeAnim)
        binding.tvSubtitle.startAnimation(fadeAnim)
        binding.tvTagline.startAnimation(fadeAnim)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2500)
    }
}
