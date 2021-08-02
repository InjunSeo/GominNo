package com.soten.gominno

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
            HomeItemUiModel(resId = assets.random(), isNew = it == 1)
        }))
    }
}

data class HomeUiModel(
    val items: List<HomeItemUiModel> = emptyList()
)

data class HomeItemUiModel(
    @DrawableRes
    val resId: Int,
    val isNew: Boolean
)
