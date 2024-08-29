package com.example.demo.data.repository

import com.example.demo.data.local.dao.UserDao
import com.example.demo.data.mapper.UserMapper
import com.example.demo.data.remote.api.GitHubApiService
import com.example.demo.domain.model.User
import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun getUsers(page: Int, perPage: Int): List<User> {
        if (page == 0) {
            val localUsers = userDao.getUsers().map { userMapper.mapToDomain(it) }
            if (localUsers.isNotEmpty()) {
                return localUsers
            }
        }

        val remoteUsers = apiService.getUsers(since = page * perPage, perPage = perPage)
            .map { userMapper.mapToDomain(it) }

        if (page == 0) {
            // Save the fetched data to the local database
            userDao.insertUsers(remoteUsers.map { userMapper.mapToEntity(it) })
        }

        return remoteUsers
    }

    override suspend fun getUserDetail(username: String): UserDetail {
        val response = apiService.getUserDetail(username)
        return userMapper.mapToDomain(response)
    }
}
