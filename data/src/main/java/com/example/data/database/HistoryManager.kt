package com.example.data.database

import android.content.Context
import com.example.data.database.model.CharactersDb


class HistoryManager(context: Context) : HistoryRepository {
    private val daoHistory = HistoryDataBase.getInstance(context).getHistoryDao()
    var repository = HistoryRealization(daoHistory)

    override suspend fun allHistory(): List<CharactersDb> = repository.allHistory()

    override suspend fun insertNote(charactersDb: CharactersDb) : Long {
        return repository.insertNote(charactersDb)
    }

    override suspend fun deleteNote(charactersDb: CharactersDb) {
        repository.deleteNote(charactersDb)
    }

    override suspend fun deleteAll() {
        repository.deleteAll()
    }

    override suspend fun getById(charactersId: Long): CharactersDb {
        return repository.getById(charactersId)
    }
}