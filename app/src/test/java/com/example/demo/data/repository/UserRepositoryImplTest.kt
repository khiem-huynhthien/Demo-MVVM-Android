package com.example.demo.data.repository

import com.example.demo.data.local.dao.UserDao
import com.example.demo.data.local.entity.UserEntity
import com.example.demo.data.mapper.UserMapper
import com.example.demo.data.remote.api.GitHubApiService
import com.example.demo.data.remote.response.UserDetailResponse
import com.example.demo.data.remote.response.UserResponse
import com.example.demo.domain.model.User
import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var apiService: GitHubApiService
    private lateinit var userDao: UserDao
    private lateinit var userMapper: UserMapper
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        apiService = mockk()
        userDao = mockk()
        userMapper = mockk()
        userRepository = UserRepositoryImpl(apiService, userDao, userMapper)
    }

    @Test
    fun getUsers_returnsLocalUsers_whenPageIsZeroAndLocalUsersExist() = runTest {
        val localUsers = listOf(mockk<UserEntity>())
        coEvery { userDao.getUsers() } returns localUsers
        every { userMapper.mapToDomain(any<UserEntity>()) } returns mockk()

        val result = userRepository.getUsers(0, 10)

        Assert.assertEquals(localUsers.size, result.size)
    }

    @Test
    fun getUsers_returnsRemoteUsers_whenPageIsZeroAndLocalUsersDoNotExist() = runTest {
        coEvery { userDao.getUsers() } returns emptyList()
        val remoteUsers = listOf(mockk<UserResponse>())
        coEvery { apiService.getUsers(any(), any()) } returns remoteUsers
        every { userMapper.mapToDomain(any<UserResponse>()) } returns mockk()
        // Mock the insertUsers method to avoid the actual database operation
        coEvery { userDao.insertUsers(any()) } returns mockk()
        every { userMapper.mapToEntity(any()) } returns mockk()

        val result = userRepository.getUsers(0, 10)

        Assert.assertEquals(remoteUsers.size, result.size)
    }

    @Test
    fun getUsers_returnsRemoteUsers_whenPageIs_1() = runTest {
        val remoteUsers = listOf(mockk<UserResponse>())
        coEvery { apiService.getUsers(any(), any()) } returns remoteUsers
        every { userMapper.mapToDomain(any<UserResponse>()) } returns mockk<User>()

        val result = userRepository.getUsers(1, 10)

        Assert.assertEquals(1, result.size)
    }

    @Test
    fun getUserDetail_returnsUserDetail() = runTest {
        val userDetail = mockk<UserDetailResponse> {
            every { login } returns "username"
        }
        coEvery { apiService.getUserDetail(any()) } returns userDetail
        every { userMapper.mapToDomain(any<UserDetailResponse>()) } returns mockk<UserDetail> {
            every { login } returns "username"
        }

        val result = userRepository.getUserDetail("username")

        Assert.assertEquals(userDetail.login, result.login)
    }
}