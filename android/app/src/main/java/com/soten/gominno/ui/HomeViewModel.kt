package com.soten.gominno

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey

class HomeViewModel : ViewModel() {

    private val assets = arrayOf(
        R.drawable.on_boarding_01,
        R.drawable.on_boarding_02,
        R.drawable.splash
    )

    private val _uiModel = MutableLiveData(HomeUiModel())
    val uiModel: LiveData<HomeUiModel>
        get() = _uiModel


    init {
        _uiModel.postValue(HomeUiModel((1..30).map {
            TestEntity(resId = assets.random(), isNew = false)
        }))
    }


}

data class HomeUiModel(
    val items: List<TestEntity> = emptyList()
)

data class TestEntity(
    @DrawableRes
    val resId: Int,
    val isNew: Boolean
)

@Entity
data class HomeItemUiModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var title: String,
    var description: String
)

