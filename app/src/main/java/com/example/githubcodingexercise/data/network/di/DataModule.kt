package com.example.githubcodingexercise.data.network.di

import com.example.githubcodingexercise.data.repository.GitHubRepositoryImpl
import com.example.githubcodingexercise.domain.repository.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsGitHubRepository(
        newsRepository: GitHubRepositoryImpl
    ): GitHubRepository
}