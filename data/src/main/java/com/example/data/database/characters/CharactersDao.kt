package com.example.data.database.characters

import androidx.room.*

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(charactersDb: CharactersDb): Long

    @Delete
    suspend fun delete(charactersDb: CharactersDb)

    @Query("DELETE FROM saved_characters")
    suspend fun deleteAllHistory()


    @Query("SELECT * FROM saved_characters")
    suspend fun getAllHistory(): List<CharactersDb>

    @Query("SELECT * FROM saved_characters WHERE id = :charactersId")
    suspend fun getById(charactersId: Long): CharactersDb?
}