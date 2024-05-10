package com.example.githubcodingexercise.data.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "top_repositories"
)
data class GitHubRepoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
    val starCount: Int,
    val ownerName: String,
    val contributorCount: Int
)