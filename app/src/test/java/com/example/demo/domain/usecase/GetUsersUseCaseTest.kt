package com.example.demo.domain.usecase

import com.example.demo.domain.model.User
import com.example.demo.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `invoke fetches users from repository`() = runBlocking {
        // Arrange
        val userList = listOf(User(login = "username", avatarUrl = "avatarUrl", htmlUrl = "htmlUrl"))
        coEvery { userRepository.getUsers(any(), any()) } returns userList

        // Act
        val result = getUsersUseCase(0)

        // Assert
        coVerify { userRepository.getUsers(any(), any()) }
        assert(result == userList)
    }
}