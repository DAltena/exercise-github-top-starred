package com.example.githubcodingexercise.data.network.model

import com.google.gson.annotations.SerializedName

data class GitHubRepoResults(
    @SerializedName("items")
    var repos: List<GitHubRepo>? = null
)