package com.example.data.repository.cache

import com.example.data.database.characters.CharactersDb

interface CharactersDataSource {
    suspend fun allHistory(): List<CharactersDb>
    suspend fun insertNote(charactersDb: CharactersDb): Long
    suspend fun deleteNote(charactersDb: CharactersDb)
    suspend fun deleteAll()
    suspend fun getById(charactersId: Long): CharactersDb?
}