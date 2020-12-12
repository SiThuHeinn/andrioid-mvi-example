package com.example.android_mvi.data.model

import com.squareup.moshi.Json

data class Post (
    @Json(name = "id")
    val id : Int = 0,
    @Json(name="userId")
    val userId : String = "",
    @Json(name="title")
    val title : String = "",
    @Json(name="body")
    val body : String = ""
)