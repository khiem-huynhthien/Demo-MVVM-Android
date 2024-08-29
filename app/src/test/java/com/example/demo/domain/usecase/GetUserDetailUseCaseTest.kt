package com.example.demo.domain.usecase

import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetUserDetailUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUserDetailUseCase = GetUserDetailUseCase(userRepository)
    }

    @Test
    fun returnsUserDetail_whenUsernameExists() = runTest {
        val userDetail = UserDetail(
            login = "username",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl",
            location = "HoChiMinh",
            followers = 123,
            following = 456
        )
        coEvery { userRepository.getUserDetail("username") } returns userDetail

        val result = getUserDetailUseCase("username")

        assertEquals(userDetail, result)
    }

    @Test
    fun throwsException_whenUsernameDoesNotExist() = runTest {
        coEvery { userRepository.getUserDetail("nonexistent") } throws Exception("User not found")

        assertFailsWith<Exception> {
            getUserDetailUseCase("nonexistent")
        }
    }

    @Test
    fun callsRepositoryWithCorrectUsername() = runTest {
        val username = "username"
        coEvery { userRepository.getUserDetail(username) } returns mockk()

        getUserDetailUseCase(username)

        coVerify { userRepository.getUserDetail(username) }
    }
}