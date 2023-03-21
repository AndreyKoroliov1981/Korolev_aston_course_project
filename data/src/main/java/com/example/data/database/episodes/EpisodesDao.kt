package com.example.data.database.episodes

import androidx.room.*

@Dao
interface EpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episodesDb: EpisodesDb): Long

    @Delete
    suspend fun delete(episodesDb: EpisodesDb)

    @Query("DELETE FROM saved_episodes")
    suspend fun deleteAllHistory()


    @Query("SELECT * FROM saved_episodes")
    suspend fun getAllHistory(): List<EpisodesDb>

    @Query("SELECT * FROM saved_episodes WHERE id = :episodesId")
    suspend fun getById(episodesId: Long): EpisodesDb
}