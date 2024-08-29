package com.example.demo.di

import com.example.demo.data.local.dao.UserDao
import com.example.demo.data.mapper.UserMapper
import com.example.demo.data.remote.api.GitHubApiService
import com.example.demo.data.repository.UserRepositoryImpl
import com.example.demo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: GitHubApiService,
        userDao: UserDao,
        userMapper: UserMapper
    ): UserRepository {
        return UserRepositoryImpl(apiService = apiService, userDao = userDao, userMapper = userMapper)
    }
}