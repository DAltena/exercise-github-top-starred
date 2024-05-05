@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.githubcodingexercise.presentation.feature.repositories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubcodingexercise.R
import com.example.githubcodingexercise.domain.model.GitHubRepo


@Composable
fun RepositoriesListScreenRoot(
    viewModel: RepositoriesListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RepositoriesListScreen(
        uiState = uiState,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RepositoriesListScreen(
    uiState: RepositoriesListState,
    onAction: (RepositoriesListAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(text = stringResource(R.string.repository_list_title))
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(RepositoriesListAction.OnRefreshDataClick)
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(R.string.refresh_fab_content_description)
                )
            }
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (uiState.repositories.isNotEmpty()) {
                LazyColumn (
                    state = lazyListState,
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed (
                        items = uiState.repositories,
                        key = { _, repo -> repo.id }
                    ) {index, repo ->
                        RepositoryListItem(
                            rank = index + 1,
                            repo = repo
                        )
                    }
                }
            } else if (!uiState.isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.repositories_empty_title),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )
                    Text(
                        text = stringResource(R.string.repositories_empty_description),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun RepositoryListItem(
    rank: Int,
    repo: GitHubRepo
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                    )
                    Text(
                        text = repo.starCount.toString(),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                repo.topContributor?.let { topContributor ->
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                    )
                    Text(
                        text = stringResource(R.string.repository_top_contributor_title),
                        textDecoration = TextDecoration.Underline,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.top_contributor_name_concat, topContributor.login),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(id = R.string.top_contributor_commits_concat, topContributor.contributions),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            Text(
                text = "#${rank}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}