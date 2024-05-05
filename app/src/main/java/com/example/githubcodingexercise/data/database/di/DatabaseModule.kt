package com.example.githubcodingexercise.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.githubcodingexercise.data.database.AppDatabase
import com.example.githubcodingexercise.data.database.dao.ContributorDao
import com.example.githubcodingexercise.data.database.dao.GitHubRepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database"
    ).build()

    @Provides
    fun gitHubRepoDao(
        database: AppDatabase,
    ): GitHubRepoDao = database.repoDao()

    @Provides
    fun contributorDao(
        database: AppDatabase,
    ): ContributorDao = database.contributorDao()
}