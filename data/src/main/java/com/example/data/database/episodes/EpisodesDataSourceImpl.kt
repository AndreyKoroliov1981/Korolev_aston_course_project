package com.example.data.database.episodes

import com.example.data.repository.cache.EpisodesDataSource
import javax.inject.Inject

class EpisodesDataSourceImpl
@Inject constructor(
    private val episodesDao: EpisodesDao
): EpisodesDataSource {

    override suspend fun allHistoryEpisodes(): List<EpisodesDb> {
        return episodesDao.getAllHistory()
    }

    override suspend fun insertNoteEpisodes(episodesDb: EpisodesDb): Long {
        return episodesDao.insert(episodesDb)
    }

    override suspend fun deleteNoteEpisodes(episodesDb: EpisodesDb) {
        episodesDao.delete(episodesDb)
    }

    override suspend fun deleteAllEpisodes() {
        episodesDao.deleteAllHistory()
    }

    override suspend fun getByIdEpisodes(episodesId: Long): EpisodesDb? {
        return episodesDao.getById(episodesId)
    }
}