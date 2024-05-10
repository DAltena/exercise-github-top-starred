package com.example.githubcodingexercise.data.repository

import com.example.githubcodingexercise.data.database.dao.ContributorDao
import com.example.githubcodingexercise.data.database.dao.GitHubRepoDao
import com.example.githubcodingexercise.data.database.model.GitHubRepoAndTopContributor
import com.example.githubcodingexercise.data.database.model.toGitHubRepoDomainModel
import com.example.githubcodingexercise.data.network.client.GitHubClient
import com.example.githubcodingexercise.data.network.model.Contributor
import com.example.githubcodingexercise.data.network.model.asDbEntities
import com.example.githubcodingexercise.data.network.model.asDbEntity
import com.example.githubcodingexercise.domain.repository.GitHubRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubClient: GitHubClient,
    private val gitHubRepoDao: GitHubRepoDao,
    private val contributorDao: ContributorDao
): GitHubRepository {

    override fun getTopReposWithContributors() = gitHubRepoDao.getTopReposFlow().map { it.map(GitHubRepoAndTopContributor::toGitHubRepoDomainModel) }

    override suspend fun refreshTopRepositories() {
        withContext(Dispatchers.IO) {
            val topRepositoriesResult = gitHubClient.getTopRepositories(token = AUTHORIZATION_HEADER)
            if (topRepositoriesResult.isSuccessful) {
                topRepositoriesResult.body()?.repos?.let { repositories ->
                    gitHubRepoDao.deleteAll()
                    gitHubRepoDao.insertTopRepos(repositories.asDbEntities())
                }
            }
        }
    }

    override suspend fun refreshTopContributors() {
        withContext(Dispatchers.IO) {
            val deferredOperations = mutableListOf<Deferred<Unit>>()
            gitHubRepoDao.getTopRepos().forEach { repo ->
                deferredOperations.add(
                    async {
                        var keepSearching = true
                        var currentPage = 1
                        var numberOfContributors = 0
                        val topContributors: MutableList<Contributor> = mutableListOf()

                        while (keepSearching && currentPage <= 3) {
                            val topContributorResult = gitHubClient.getTopContributorForRepository(
                                token = AUTHORIZATION_HEADER,
                                owner = repo.ownerName,
                                repo = repo.name,
                                perPage = 1,
                                page = currentPage
                            )
                            if (topContributorResult.isSuccessful) {
                                // TODO reference here in how to possibly get total count, though comments imply it may not work properly: https://stackoverflow.com/questions/44347339/github-api-how-efficiently-get-the-total-contributors-amount-per-repository
                                topContributorResult.body()?.firstOrNull()?.let {
                                   topContributors.add(it)
                                } ?: run {
                                    keepSearching = false
                                }
                            } else {
                                keepSearching = false
                            }
                            currentPage += 1
                        }
                        contributorDao.insertTopContributors(topContributors.map { it.asDbEntity(repoId = repo.id) })
                        gitHubRepoDao.updateRepoContributorCount(repoId = repo.id, contributorCount = numberOfContributors)
                    }
                )
            }
            deferredOperations.awaitAll()
        }
    }

    override suspend fun deleteData() {
        withContext(Dispatchers.IO) {
            gitHubRepoDao.deleteAll()
            contributorDao.deleteAll()
        }
    }

    companion object {
        // TODO provide "token <oauth_token>"
        private const val AUTHORIZATION_HEADER = ""
    }
}