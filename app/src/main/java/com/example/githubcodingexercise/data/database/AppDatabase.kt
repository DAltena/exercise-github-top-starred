package com.example.githubcodingexercise.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubcodingexercise.data.database.dao.ContributorDao
import com.example.githubcodingexercise.data.database.dao.GitHubRepoDao
import com.example.githubcodingexercise.data.database.model.entity.ContributorEntity
import com.example.githubcodingexercise.data.database.model.entity.GitHubRepoEntity

@Database(entities = [GitHubRepoEntity::class, ContributorEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repoDao(): GitHubRepoDao
    abstract fun contributorDao(): ContributorDao
}