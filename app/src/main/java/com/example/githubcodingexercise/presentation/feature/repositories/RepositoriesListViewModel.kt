package com.example.githubcodingexercise.presentation.feature.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubcodingexercise.domain.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
): ViewModel() {

    var uiState: StateFlow<RepositoriesListState>

    private var isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        uiState = combine(
            isLoading,
            gitHubRepository.getTopReposWithContributors(),
        ) { isLoading, topRepos ->
            RepositoriesListState(isLoading, topRepos)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepositoriesListState()
        )
    }

    fun onAction(action: RepositoriesListAction) {
        when (action) {
            RepositoriesListAction.OnRefreshDataClick -> refreshData()
            RepositoriesListAction.OnDeleteDataClick -> deleteData()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            isLoading.emit(true)
            gitHubRepository.refreshTopRepositories()
            gitHubRepository.refreshTopContributors()
            isLoading.emit(false)
        }
    }

    private fun deleteData() {
        viewModelScope.launch {
            gitHubRepository.deleteData()
        }
    }
}