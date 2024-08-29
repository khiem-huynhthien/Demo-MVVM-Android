package com.example.demo.presentation.viewmodel

import com.example.demo.domain.model.User
import com.example.demo.domain.usecase.GetUsersUseCase
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getUsersUseCase = mockk()
        viewModel = MainViewModel(getUsersUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUsers loads users and updates state`() = runTest {
        // Arrange
        val userList = listOf(
            User(login = "username", avatarUrl = "avatarUrl", htmlUrl = "htmlUrl")
        )
        coEvery { getUsersUseCase.invoke(any()) } returns userList

        // Act
        viewModel.onEvent(MainEvent.LoadData)

        // Move to the next step in the coroutine
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.first()
        Assert.assertEquals(state.users, userList)
    }

    @Test
    fun `loadUsers handles error state`() = runTest {
        // Arrange
        val errorMessage = "Error loading users"
        coEvery { getUsersUseCase.invoke(any()) } throws RuntimeException(errorMessage)

        // Act
        viewModel.onEvent(MainEvent.LoadMore)

        // Move to the next step in the coroutine
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.first()
        Assert.assertEquals(state.errorMessage, "Failed to load data $errorMessage")
    }
}