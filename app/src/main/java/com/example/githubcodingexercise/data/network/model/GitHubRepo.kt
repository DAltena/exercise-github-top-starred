package com.example.githubcodingexercise.data.network.model

import com.example.githubcodingexercise.data.database.model.entity.GitHubRepoEntity
import com.google.gson.annotations.SerializedName

data class GitHubRepo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("stargazers_count")
    val starCount: Int,
    @SerializedName("owner")
    val owner: GitHubRepoOwner
)

data class GitHubRepoOwner(
    @SerializedName("login")
    val name: String
)

fun List<GitHubRepo>.asDbEntities() =
    map { gitHubRepo ->
        GitHubRepoEntity(
            id = gitHubRepo.id,
            name = gitHubRepo.name,
            url = gitHubRepo.url,
            starCount = gitHubRepo.starCount,
            ownerName = gitHubRepo.owner.name,
            contributorCount = 0
        )
    }