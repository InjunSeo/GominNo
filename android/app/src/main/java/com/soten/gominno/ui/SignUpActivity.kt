package com.soten.gominno.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soten.gominno.R
import com.soten.gominno.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}