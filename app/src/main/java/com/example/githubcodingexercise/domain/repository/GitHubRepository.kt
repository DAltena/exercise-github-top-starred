package com.example.githubcodingexercise.domain.repository

import com.example.githubcodingexercise.domain.model.GitHubRepo
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getTopReposWithContributors(): Flow<List<GitHubRepo>>
    suspend fun refreshTopRepositories()
    suspend fun refreshTopContributors()
    suspend fun deleteData()
}