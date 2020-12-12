package com.example.android_mvi.Inent

sealed class MainIntent {
    object FetchPost : MainIntent()
}