package com.example.githubcodingexercise.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubcodingexercise.data.database.model.entity.ContributorEntity

@Dao
interface ContributorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopContributor(contributorEntity: ContributorEntity)

    @Query("DELETE FROM top_contributors")
    suspend fun deleteAll()
}