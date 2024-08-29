package com.example.demo.domain.repository

import com.example.demo.domain.model.User
import com.example.demo.domain.model.UserDetail

interface UserRepository {
    suspend fun getUsers(page: Int, perPage: Int): List<User>
    suspend fun getUserDetail(username: String): UserDetail
}