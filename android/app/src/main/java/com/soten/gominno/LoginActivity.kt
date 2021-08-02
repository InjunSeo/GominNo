package com.soten.gominno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soten.gominno.databinding.ActivityLoginBinding
import com.soten.gominno.ui.ContentSelectActivity

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, ContentSelectActivity::class.java))
            finish()
        }
    }


}