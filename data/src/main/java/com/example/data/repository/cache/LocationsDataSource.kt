package com.example.data.repository.cache

import com.example.data.database.locations.LocationsDb

interface LocationsDataSource {
    suspend fun allHistoryLocations(): List<LocationsDb>
    suspend fun insertNoteLocations(locationsDb: LocationsDb): Long
    suspend fun deleteNoteLocations(locationsDb: LocationsDb)
    suspend fun deleteAllLocations()
    suspend fun getByIdLocations(locationsId: Long): LocationsDb?
}