package com.soten.gominno.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import com.soten.gominno.GlobalData
import com.soten.gominno.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (GlobalData.currentUser) {
            startActivity(Intent(this, ContentSelectActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, ContentSelectActivity::class.java))
            finish()
        }

        binding.signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun onBackPressed() {
        FinishFragment().show(supportFragmentManager, "finish")
    }

}