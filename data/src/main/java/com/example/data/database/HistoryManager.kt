package com.example.data.database

import android.content.Context
import com.example.data.database.characters.CharactersDb
import com.example.data.database.characters.HistoryRealization
import com.example.data.database.episodes.EpisodesDb
import com.example.data.database.episodes.HistoryRealizationEpisodes
import com.example.data.database.locations.HistoryRealizationLocations
import com.example.data.database.locations.LocationsDb
import com.example.data.repository.cache.HistoryRepositoryCharacters
import com.example.data.repository.cache.HistoryRepositoryEpisodes
import com.example.data.repository.cache.HistoryRepositoryLocations

class HistoryManager(context: Context) : HistoryRepositoryCharacters, HistoryRepositoryLocations,
    HistoryRepositoryEpisodes {
    private val daoHistory = HistoryDataBase.getInstance(context).getHistoryDao()

    private val daoLocations = HistoryDataBase.getInstance(context).getLocationsDao()

    private val daoEpisodes = HistoryDataBase.getInstance(context).getEpisodesDao()

    private var repository = HistoryRealization(daoHistory)

    private var repositoryLocations = HistoryRealizationLocations(daoLocations)

    private var repositoryEpisodes = HistoryRealizationEpisodes(daoEpisodes)

    override suspend fun allHistory(): List<CharactersDb> = repository.allHistory()

    override suspend fun insertNote(charactersDb: CharactersDb): Long {
        return repository.insertNote(charactersDb)
    }

    override suspend fun deleteNote(charactersDb: CharactersDb) {
        repository.deleteNote(charactersDb)
    }

    override suspend fun deleteAll() {
        repository.deleteAll()
    }

    override suspend fun getById(charactersId: Long): CharactersDb? {
        return repository.getById(charactersId)
    }

    override suspend fun allHistoryLocations(): List<LocationsDb> {
        return repositoryLocations.allHistory()
    }

    override suspend fun insertNoteLocations(locationsDb: LocationsDb): Long {
        return repositoryLocations.insertNote(locationsDb)
    }

    override suspend fun deleteNoteLocations(locationsDb: LocationsDb) {
        repositoryLocations.deleteNote(locationsDb)
    }

    override suspend fun deleteAllLocations() {
        repositoryLocations.deleteAll()
    }

    override suspend fun getByIdLocations(locationsId: Long): LocationsDb? {
        return repositoryLocations.getById(locationsId)
    }

    override suspend fun allHistoryEpisodes(): List<EpisodesDb> {
        return repositoryEpisodes.allHistory()
    }

    override suspend fun insertNoteEpisodes(episodesDb: EpisodesDb): Long {
        return repositoryEpisodes.insertNote(episodesDb)
    }

    override suspend fun deleteNoteEpisodes(episodesDb: EpisodesDb) {
        repositoryEpisodes.deleteNote(episodesDb)
    }

    override suspend fun deleteAllEpisodes() {
        repositoryEpisodes.deleteAll()
    }

    override suspend fun getByIdEpisodes(episodesId: Long): EpisodesDb? {
        return repositoryEpisodes.getById(episodesId)
    }
}