package com.soten.gominno.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.soten.gominno.board.DailyWorryOnBoardActivity
import com.soten.gominno.databinding.ActivityContentSelectBinding

class ContentSelectActivity : AppCompatActivity() {

    private val binding by lazy { ActivityContentSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() {
        binding.dailyWorryImageView.setOnClickListener {
            startActivity(
                Intent(this, DailyWorryOnBoardActivity::class.java)
            )
            finish()
        }

        binding.professionalView.setOnClickListener {
            Toast.makeText(this, "추후 오픈 예정입니다!!", Toast.LENGTH_SHORT).show()
        }
    }
}