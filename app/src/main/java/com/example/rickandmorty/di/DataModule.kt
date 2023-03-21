package com.example.rickandmorty.di

import android.content.Context
import com.example.data.database.HistoryManager
import com.example.data.database.HistoryRepository
import com.example.data.network.characters.CharactersCommon
import com.example.data.network.characters.CharactersRetrofitService
import com.example.data.network.episodes.EpisodesCommon
import com.example.data.network.episodes.EpisodesRetrofitService
import com.example.data.network.locations.LocationsCommon
import com.example.data.network.locations.LocationsRetrofitService
import com.example.data.network.personage.PersonageCommon
import com.example.data.network.personage.PersonageRetrofitService
import com.example.data.network.place.PlaceCommon
import com.example.data.network.place.PlaceRetrofitService
import com.example.data.repository.characters.CharactersMapper
import com.example.data.repository.characters.CharactersRepositoryImpl
import com.example.data.repository.episodes.EpisodesMapper
import com.example.data.repository.episodes.EpisodesRepositoryImpl
import com.example.data.repository.locations.LocationsMapper
import com.example.data.repository.locations.LocationsRepositoryImpl
import com.example.data.repository.personage.EpisodeMapper
import com.example.data.repository.personage.LocationeMapper
import com.example.data.repository.personage.PersonageRepositoryImpl
import com.example.data.repository.place.PlaceMapper
import com.example.data.repository.place.PlaceRepositoryImpl
import com.example.domain.characters.CharactersRepository
import com.example.domain.episodes.EpisodesRepository
import com.example.domain.locations.LocationsRepository
import com.example.domain.personage.PersonageRepository
import com.example.domain.place.PlaceRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideCharactersRepository(
        charactersMapper: CharactersMapper,
        charactersRetrofitService: CharactersRetrofitService,
        historyRepository : HistoryRepository
    ): CharactersRepository {
        return CharactersRepositoryImpl(
            charactersMapper = charactersMapper,
            charactersRetrofitService = charactersRetrofitService,
            historyRepository = historyRepository
        )
    }

    @Provides
    fun provideCharactersMapper(): CharactersMapper {
        return CharactersMapper()
    }

    @Provides
    fun provideCharactersRetrofitService(): CharactersRetrofitService {
        return CharactersCommon.charactersRetrofitService
    }

    @Provides
    fun providePersonageRepository(
        episodeMapper: EpisodeMapper,
        locationeMapper: LocationeMapper,
        personageRetrofitService: PersonageRetrofitService
    ): PersonageRepository {
        return PersonageRepositoryImpl(
            episodeMapper = episodeMapper,
            locationeMapper = locationeMapper,
            personageRetrofitService = personageRetrofitService
        )
    }

    @Provides
    fun provideLocationeMapper(): LocationeMapper {
        return LocationeMapper()
    }

    @Provides
    fun provideEpisodeMapper(): EpisodeMapper {
        return EpisodeMapper()
    }

    @Provides
    fun providePersonageRetrofitService(): PersonageRetrofitService {
        return PersonageCommon.personageRetrofitService
    }

    @Provides
    fun provideEpisodesRepository(
        episodesMapper: EpisodesMapper,
        episodesRetrofitService: EpisodesRetrofitService
    ): EpisodesRepository {
        return EpisodesRepositoryImpl(
            episodesMapper = episodesMapper,
            episodesRetrofitService = episodesRetrofitService
        )
    }

    @Provides
    fun provideEpisodesMapper(): EpisodesMapper {
        return EpisodesMapper()
    }

    @Provides
    fun provideEpisodesRetrofitService(): EpisodesRetrofitService {
        return EpisodesCommon.episodesRetrofitService
    }

    @Provides
    fun provideLocationsRepository(
        locationsMapper: LocationsMapper,
        locationsRetrofitService: LocationsRetrofitService
    ): LocationsRepository {
        return LocationsRepositoryImpl(
            locationsMapper = locationsMapper,
            locationsRetrofitService = locationsRetrofitService
        )
    }

    @Provides
    fun provideLocationsMapper(): LocationsMapper {
        return LocationsMapper()
    }

    @Provides
    fun provideLocationsRetrofitService(): LocationsRetrofitService {
        return LocationsCommon.locationsRetrofitService
    }

    @Provides
    fun providePlaceRepository(
        placeMapper: PlaceMapper,
        placeRetrofitService: PlaceRetrofitService
    ): PlaceRepository {
        return PlaceRepositoryImpl(
            placeMapper = placeMapper,
            placeRetrofitService = placeRetrofitService
        )
    }

    @Provides
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides
    fun providePlaceRetrofitService(): PlaceRetrofitService {
        return PlaceCommon.placeRetrofitService
    }

    @Provides
    fun provideHistoryRepository(context: Context): HistoryRepository {
        return HistoryManager(context = context)
    }
}