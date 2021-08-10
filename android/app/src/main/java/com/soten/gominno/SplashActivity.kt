package com.soten.gominno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.soten.gominno.board.AppStartOnBoardingActivity
import com.soten.gominno.ui.ContentSelectActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            if (GlobalData.showOnBoarding.not()) {
                startActivity(Intent(this, AppStartOnBoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, ContentSelectActivity::class.java))
                finish()
            }
        }, 1000)
    }
}