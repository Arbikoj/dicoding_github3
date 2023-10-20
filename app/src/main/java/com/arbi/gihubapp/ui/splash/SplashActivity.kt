package com.arbi.gihubapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.arbi.gihubapp.R
import com.arbi.gihubapp.databinding.ActivitySplashBinding
import com.arbi.gihubapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splash()
        goToHome()
    }

    private fun splash() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.appName.animation = anim

    }
    private fun goToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 3000L)
    }
}