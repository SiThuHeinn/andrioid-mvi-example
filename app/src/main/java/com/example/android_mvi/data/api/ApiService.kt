package com.example.android_mvi.data.api

import com.example.android_mvi.data.model.Post
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun fetchUsers() : List<Post>
}