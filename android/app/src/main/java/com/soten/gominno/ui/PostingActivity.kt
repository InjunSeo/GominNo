package com.soten.gominno.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.soten.gominno.HomeItemUiModel
import com.soten.gominno.databinding.ActivityPostingBinding
import com.soten.gominno.db.AppDatabase

class PostingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPostingBinding.inflate(layoutInflater) }

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()

        binding.postButton.setOnClickListener {
            Thread {
                db.PostDao().insert(
                    HomeItemUiModel(
                        id = db.PostDao().getAllPost().size,
                        title = binding.inputTitleEditText.text.toString(),
                        description = binding.inputDescriptionEditText.text.toString()
                    )
                )
            }.start()
            finish()
        }
    }
}