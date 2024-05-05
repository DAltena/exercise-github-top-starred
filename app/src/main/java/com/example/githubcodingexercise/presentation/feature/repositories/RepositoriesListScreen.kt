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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    var deleteDataDialogVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.repository_list_title))
                },
                actions = {
                    IconButton(
                        onClick = {
                            deleteDataDialogVisible = true
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_fill0_wght400_grad0_opsz24),
                            contentDescription = stringResource(R.string.delete_data_button_content_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
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
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh_fill0_wght400_grad0_opsz24),
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

            if (deleteDataDialogVisible) {
                DeleteDataDialog(
                    onDismiss = { deleteDataDialogVisible = false },
                    onConfirm = { onAction(RepositoriesListAction.OnDeleteDataClick) }
                )
            }
        }
    }
}

@Composable
private fun RepositoryListItem(
    rank: Int,
    repo: GitHubRepo
) {
    OutlinedCard(
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_star_fill0_wght400_grad0_opsz20),
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

@Composable
private fun DeleteDataDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Delete Data")
        },
        text = {
            Text(text = "Are you sure you want to delete all data?")
        }
    )
}