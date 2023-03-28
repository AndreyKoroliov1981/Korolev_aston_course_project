package com.example.data.database.locations

import com.example.data.repository.cache.LocationsDataSource
import javax.inject.Inject

class LocationsDataSourceImpl
@Inject constructor(
    private val locationsDao: LocationsDao
) : LocationsDataSource {

    override suspend fun allHistoryLocations(): List<LocationsDb> {
        return locationsDao.getAllHistory()
    }

    override suspend fun insertNoteLocations(locationsDb: LocationsDb): Long {
        return locationsDao.insert(locationsDb)
    }

    override suspend fun deleteNoteLocations(locationsDb: LocationsDb) {
        locationsDao.delete(locationsDb)
    }

    override suspend fun deleteAllLocations() {
        locationsDao.deleteAllHistory()
    }

    override suspend fun getByIdLocations(locationsId: Long): LocationsDb? {
        return locationsDao.getById(locationsId)
    }
}