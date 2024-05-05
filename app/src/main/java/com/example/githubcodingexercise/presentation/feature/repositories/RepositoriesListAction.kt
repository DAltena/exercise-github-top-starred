package com.example.githubcodingexercise.presentation.feature.repositories

sealed interface RepositoriesListAction {
    data object OnRefreshDataClick: RepositoriesListAction
}