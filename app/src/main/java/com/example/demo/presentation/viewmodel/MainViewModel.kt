package com.example.demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.model.User
import com.example.demo.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasMore: Boolean = true
)

sealed class MainEvent {
    data object LoadData : MainEvent()
    data object LoadMore : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private var currentPage = 0

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.LoadData -> loadUsers()
            is MainEvent.LoadMore -> loadUsers()
        }
    }

    private fun loadUsers() {
        if (_state.value.isLoading || !_state.value.hasMore) return

        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val newUsers = getUsersUseCase.invoke(page = currentPage)
                _state.value = _state.value.copy(
                    users = _state.value.users + newUsers,
                    isLoading = false,
                    hasMore = newUsers.isNotEmpty()
                )
                currentPage++
            } catch (ex: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load data ${ex.message}"
                )
            }
        }
    }
}