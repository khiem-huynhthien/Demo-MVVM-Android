package com.example.demo.data.remote.response

import com.google.gson.annotations.SerializedName

class UserDetailResponse(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("location") val location: String?,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)