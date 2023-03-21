package com.example.data.database

import com.example.data.database.model.CharactersDb

interface HistoryRepository {
    suspend fun allHistory(): List<CharactersDb>
    suspend fun insertNote(charactersDb: CharactersDb): Long
    suspend fun deleteNote(charactersDb: CharactersDb)
    suspend fun deleteAll()
    suspend fun getById(charactersId: Long): CharactersDb
}