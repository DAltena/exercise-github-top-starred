package com.example.githubcodingexercise.data.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubcodingexercise.domain.model.Contributor

@Entity(
    tableName = "top_contributors"
)
data class ContributorEntity(
    @PrimaryKey
    val repoId: Int,
    val id: Int,
    val login: String,
    val contributions: Int
)

fun ContributorEntity.asDomainModel() = Contributor(
    id = id,
    login = login,
    contributions = contributions
)