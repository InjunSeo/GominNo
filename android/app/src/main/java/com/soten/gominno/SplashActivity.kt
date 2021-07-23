package com.soten.gominno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1500)
    }

    override fun onStart() {
        super.onStart()

        startActivity(Intent(this, OnBoardingActivity::class.java))
    }
}