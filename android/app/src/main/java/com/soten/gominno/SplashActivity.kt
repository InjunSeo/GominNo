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
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (GlobalData.showOnBoarding.not()) {
                startActivity(Intent(this, AppStartOnBoardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, ContentSelectActivity::class.java))
                finish()
            }
        }, 3000L)
    }
}