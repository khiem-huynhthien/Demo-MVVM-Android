package com.example.demo.presentation.viewmodel

import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.usecase.GetUserDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    private lateinit var viewModel: UserViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getUserDetailUseCase = mockk()
        viewModel = UserViewModel(getUserDetailUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchUserDetails_updatesStateWithUserDetail_whenUsernameExists() = runTest {
        val userDetail = UserDetail(
            login = "username",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl",
            location = "HoChiMinh",
            followers = 123,
            following = 456
        )
        coEvery { getUserDetailUseCase.invoke("username") } returns userDetail

        viewModel.fetchUserDetails("username")
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(userDetail, state.user)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun fetchUserDetails_updatesStateWithError_whenUsernameDoesNotExist() = runTest {
        val errorMessage = "User not found"
        coEvery { getUserDetailUseCase.invoke("nonexistent") } throws Exception(errorMessage)

        viewModel.fetchUserDetails("nonexistent")
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(null, state.user)
        assertEquals(false, state.isLoading)
        assertEquals("Failed to load data $errorMessage", state.errorMessage)
    }

    @Test
    fun fetchUserDetails_doesNotUpdateState_whenAlreadyLoading() = runTest {
        val userDetail = UserDetail(
            login = "username",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl",
            location = "HoChiMinh",
            followers = 123,
            following = 456
        )
        coEvery { getUserDetailUseCase.invoke("username") } returns userDetail

        viewModel.fetchUserDetails("username")
        viewModel.fetchUserDetails("username")
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(userDetail, state.user)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.errorMessage)
    }
}