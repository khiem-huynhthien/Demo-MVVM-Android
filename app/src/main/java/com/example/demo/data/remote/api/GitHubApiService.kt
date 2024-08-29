package com.example.demo.data.remote.api

import com.example.demo.data.remote.response.UserDetailResponse
import com.example.demo.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): List<UserResponse>

    @GET("users/{login_username}")
    suspend fun getUserDetail(
        @Path("login_username") username: String,
    ): UserDetailResponse
}