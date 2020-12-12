package com.example.android_mvi.data.api

import com.example.android_mvi.data.model.Post

interface ApiHelper {
    suspend fun fetchPosts() : List<Post>
}