package com.example.data.database.episodes

class HistoryRealizationEpisodes(private val historyDao: EpisodesDao) {

    suspend fun allHistory(): List<EpisodesDb> {
        return historyDao.getAllHistory()
    }

    suspend fun insertNote(historyModel: EpisodesDb): Long {
        return historyDao.insert(historyModel)
    }

    suspend fun deleteNote(historyModel: EpisodesDb) {
        historyDao.delete(historyModel)
    }

    suspend fun deleteAll() {
        historyDao.deleteAllHistory()
    }

    suspend fun getById(recordId: Long): EpisodesDb? {
        return historyDao.getById(recordId)
    }
}