package com.example.githubcodingexercise.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.githubcodingexercise.data.database.model.entity.ContributorEntity
import com.example.githubcodingexercise.data.database.model.entity.GitHubRepoEntity
import com.example.githubcodingexercise.data.database.model.entity.asDomainModel
import com.example.githubcodingexercise.domain.model.GitHubRepo

data class GitHubRepoAndTopContributor(
    @Embedded
    val gitHubRepoEntity: GitHubRepoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "repoId"
    )
    val contributors: List<ContributorEntity>?
)

fun GitHubRepoAndTopContributor.toGitHubRepoDomainModel() = GitHubRepo(
    id = gitHubRepoEntity.id,
    name = gitHubRepoEntity.name,
    url = gitHubRepoEntity.url,
    starCount = gitHubRepoEntity.starCount,
    topContributors = contributors?.map { it.asDomainModel() },
    contributorCount = gitHubRepoEntity.contributorCount
)
