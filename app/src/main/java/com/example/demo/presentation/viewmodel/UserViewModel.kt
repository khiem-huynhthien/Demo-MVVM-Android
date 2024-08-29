package com.example.demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserState(
    val user: UserDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    fun fetchUserDetails(username: String) {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val newUser = getUserDetailUseCase.invoke(username)
                _state.value = _state.value.copy(
                    user = newUser,
                    isLoading = false,
                )
            } catch (ex: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load data ${ex.message}"
                )
            }
        }
    }
}
