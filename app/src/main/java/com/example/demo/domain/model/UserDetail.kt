package com.example.demo.domain.model

data class UserDetail(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int,
    val following: Int
)