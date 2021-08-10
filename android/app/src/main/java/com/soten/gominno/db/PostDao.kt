package com.soten.gominno.db

import androidx.room.*
import com.soten.gominno.HomeItemUiModel

@Dao
interface PostDao {

    @Query("SELECT * FROM HomeItemUiModel")
    fun getAllPost(): List<HomeItemUiModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postEntity: HomeItemUiModel)

}