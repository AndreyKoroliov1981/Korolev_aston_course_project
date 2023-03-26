package com.example.data.repository.cache

import com.example.data.database.episodes.EpisodesDb

interface EpisodesHistoryRepository {
    suspend fun allHistoryEpisodes(): List<EpisodesDb>
    suspend fun insertNoteEpisodes(episodesDb: EpisodesDb): Long
    suspend fun deleteNoteEpisodes(episodesDb: EpisodesDb)
    suspend fun deleteAllEpisodes()
    suspend fun getByIdEpisodes(episodesId: Long): EpisodesDb?
}