package com.example.githubcodingexercise.presentation.feature.repositories

import com.example.githubcodingexercise.domain.model.GitHubRepo

data class RepositoriesListState(
    val isLoading: Boolean = false,
    val repositories: List<GitHubRepo> = emptyList(),
)