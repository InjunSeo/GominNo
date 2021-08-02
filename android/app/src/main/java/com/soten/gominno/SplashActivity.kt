package com.soten.gominno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soten.gominno.board.AppStartOnBoardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)
    }

    override fun onStart() {
        super.onStart()

        startActivity(Intent(this, AppStartOnBoardingActivity::class.java))
        finish()
    }
}