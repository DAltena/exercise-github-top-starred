package com.example.githubcodingexercise.domain.model

data class GitHubRepo(
    val id: Int,
    val name: String,
    val url: String,
    val starCount: Int,
    val topContributor: Contributor? = null
)