package com.example.demo.domain.usecase

import com.example.demo.domain.model.UserDetail
import com.example.demo.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): UserDetail {
        return userRepository.getUserDetail(username)
    }
}