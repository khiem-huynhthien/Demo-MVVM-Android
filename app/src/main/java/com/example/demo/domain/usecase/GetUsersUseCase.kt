package com.example.demo.domain.usecase

import com.example.demo.domain.model.User
import com.example.demo.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    companion object {
        const val PER_PAGE = 20
    }

    suspend operator fun invoke(page: Int): List<User> {
        return userRepository.getUsers(page, PER_PAGE)
    }
}