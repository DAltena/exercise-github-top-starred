package com.example.githubcodingexercise.data.network.model

import com.example.githubcodingexercise.data.database.model.entity.ContributorEntity
import com.google.gson.annotations.SerializedName

data class Contributor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("contributions")
    val contributions: Int
)

fun Contributor.asDbEntity(repoId: Int) =
    ContributorEntity(
        repoId = repoId,
        id = id,
        login = login,
        contributions = contributions
    )