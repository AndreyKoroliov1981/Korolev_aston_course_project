package com.example.data.database.locations

import androidx.room.*

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationsDb: LocationsDb): Long

    @Delete
    suspend fun delete(locationsDb: LocationsDb)

    @Query("DELETE FROM saved_locations")
    suspend fun deleteAllHistory()


    @Query("SELECT * FROM saved_locations")
    suspend fun getAllHistory(): List<LocationsDb>

    @Query("SELECT * FROM saved_locations WHERE id = :locationsId")
    suspend fun getById(locationsId: Long): LocationsDb
}