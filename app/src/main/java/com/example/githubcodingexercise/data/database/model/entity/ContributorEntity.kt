package com.example.githubcodingexercise.data.database.model.entity

import androidx.room.Entity
import com.example.githubcodingexercise.domain.model.Contributor

@Entity(
    tableName = "top_contributors",
    primaryKeys = ["id", "repoId"]
)
data class ContributorEntity(
    val id: Int,
    val repoId: Int,
    val login: String,
    val contributions: Int
)

fun ContributorEntity.asDomainModel() = Contributor(
    id = id,
    login = login,
    contributions = contributions
)