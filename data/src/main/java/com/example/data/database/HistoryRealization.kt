package com.example.data.database

import com.example.data.database.model.CharactersDb

class HistoryRealization(private val historyDao: CharactersDao) {

    suspend fun allHistory(): List<CharactersDb> {
        return historyDao.getAllHistory()
    }

    suspend fun insertNote(historyModel: CharactersDb): Long {
        return historyDao.insert(historyModel)
    }

    suspend fun deleteNote(historyModel: CharactersDb) {
        historyDao.delete(historyModel)
    }

    suspend fun deleteAll() {
        historyDao.deleteAllHistory()
    }

    suspend fun getById(recordId: Long): CharactersDb {
        return historyDao.getById(recordId)
    }
}