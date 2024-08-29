package com.example.demo.data.mapper

import com.example.demo.data.local.entity.UserEntity
import com.example.demo.data.remote.response.UserDetailResponse
import com.example.demo.data.remote.response.UserResponse
import com.example.demo.domain.model.User
import com.example.demo.domain.model.UserDetail

class UserMapper {

    fun mapToDomain(response: UserResponse) = User(
        login = response.login,
        avatarUrl = response.avatarUrl,
        htmlUrl = response.htmlUrl
    )

    fun mapToDomain(userEntity: UserEntity): User {
        return User(
            login = userEntity.id,
            avatarUrl = userEntity.avatarUrl,
            htmlUrl = userEntity.htmlUrl
        )
    }

    fun mapToEntity(user: User): UserEntity {
        return UserEntity(
            id = user.login,
            htmlUrl = user.htmlUrl,
            avatarUrl = user.avatarUrl
        )
    }

    fun mapToDomain(response: UserDetailResponse) = UserDetail(
        login = response.login,
        avatarUrl = response.avatarUrl,
        htmlUrl = response.htmlUrl,
        location = response.location,
        followers = response.followers,
        following = response.following
    )
}