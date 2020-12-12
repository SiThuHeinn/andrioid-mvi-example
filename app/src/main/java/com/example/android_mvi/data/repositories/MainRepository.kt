package com.example.android_mvi.data.repositories

import com.example.android_mvi.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun fetchPosts() = apiHelper.fetchPosts()
}