package com.example.githubcodingexercise.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubcodingexercise.data.database.model.GitHubRepoAndTopContributor
import com.example.githubcodingexercise.data.database.model.entity.GitHubRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubRepoDao {
    @Query("SELECT * FROM top_repositories ORDER BY starCount DESC")
    suspend fun getTopRepos(): List<GitHubRepoEntity>

    @Query("SELECT * FROM top_repositories ORDER BY starCount DESC")
    fun getTopReposFlow(): Flow<List<GitHubRepoAndTopContributor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRepos(topicEntities: List<GitHubRepoEntity>)

    @Query("UPDATE top_repositories SET contributorCount=:contributorCount WHERE id=:repoId")
    suspend fun updateRepoContributorCount(repoId: Int, contributorCount: Int)

    @Query("DELETE FROM top_repositories")
    suspend fun deleteAll()
}