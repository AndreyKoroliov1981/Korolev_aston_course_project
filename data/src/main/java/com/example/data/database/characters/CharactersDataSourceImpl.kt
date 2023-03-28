package com.example.data.database.characters

import com.example.data.repository.cache.CharactersDataSource
import javax.inject.Inject

class CharactersDataSourceImpl
@Inject constructor(
    private val charactersDao: CharactersDao
) : CharactersDataSource {

    override suspend fun allHistory(): List<CharactersDb> {
        return charactersDao.getAllHistory()
    }

    override suspend fun insertNote(charactersDb: CharactersDb): Long {
        return charactersDao.insert(charactersDb)
    }

    override suspend fun deleteNote(charactersDb: CharactersDb) {
        charactersDao.delete(charactersDb)
    }

    override suspend fun deleteAll() {
        charactersDao.deleteAllHistory()
    }

    override suspend fun getById(charactersId: Long): CharactersDb? {
        return charactersDao.getById(charactersId)
    }
}