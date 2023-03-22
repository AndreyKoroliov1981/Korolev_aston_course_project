package com.example.data.database.locations

class HistoryRealizationLocations(private val historyDao: LocationsDao) {

    suspend fun allHistory(): List<LocationsDb> {
        return historyDao.getAllHistory()
    }

    suspend fun insertNote(historyModel: LocationsDb): Long {
        return historyDao.insert(historyModel)
    }

    suspend fun deleteNote(historyModel: LocationsDb) {
        historyDao.delete(historyModel)
    }

    suspend fun deleteAll() {
        historyDao.deleteAllHistory()
    }

    suspend fun getById(recordId: Long): LocationsDb? {
        return historyDao.getById(recordId)
    }
}