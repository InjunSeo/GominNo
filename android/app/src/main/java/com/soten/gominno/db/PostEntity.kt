package com.soten.gominno.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var Description: String
): Parcelable
