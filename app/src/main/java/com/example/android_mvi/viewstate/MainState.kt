package com.example.android_mvi.viewstate

import com.example.android_mvi.data.model.Post

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Posts(val posts : List<Post>) : MainState()
    data class Error(val message : String?) : MainState()
}