package com.example.demo.data.remote.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String
)