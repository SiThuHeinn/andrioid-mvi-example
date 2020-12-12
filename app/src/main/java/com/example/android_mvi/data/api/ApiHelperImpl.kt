package com.example.android_mvi.data.api

import com.example.android_mvi.data.model.Post

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun fetchPosts(): List<Post> {
        return apiService.fetchUsers()

    }
}