package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.CharactersInteractorImpl
import com.example.domain.characters.CharactersRepository
import com.example.domain.episodes.EpisodesInteractor
import com.example.domain.episodes.EpisodesInteractorImpl
import com.example.domain.episodes.EpisodesRepository
import com.example.domain.locations.LocationsInteractor
import com.example.domain.locations.LocationsInteractorImpl
import com.example.domain.locations.LocationsRepository
import com.example.domain.personage.PersonageInteractor
import com.example.domain.personage.PersonageInteractorImpl
import com.example.domain.personage.PersonageRepository
import com.example.domain.place.PlaceInteractor
import com.example.domain.place.PlaceInteractorImpl
import com.example.domain.place.PlaceRepository
import com.example.domain.series.SeriesInteractor
import com.example.domain.series.SeriesInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideCharactersInteractor(charactersRepository: CharactersRepository): CharactersInteractor {
        return CharactersInteractorImpl(charactersRepository = charactersRepository)
    }

    @Provides
    fun providePersonageInteractor(
        personageRepository: PersonageRepository
    ): PersonageInteractor {
        return PersonageInteractorImpl(
            personageRepository = personageRepository
        )
    }

    @Provides
    fun provideEpisodesInteractor(episodesRepository: EpisodesRepository): EpisodesInteractor {
        return EpisodesInteractorImpl(episodesRepository = episodesRepository)
    }

    @Provides
    fun provideLocationsInteractor(locationsRepository: LocationsRepository): LocationsInteractor {
        return LocationsInteractorImpl(locationsRepository = locationsRepository)
    }

    @Provides
    fun providePlaceInteractor(
        placeRepository: PlaceRepository
    ): PlaceInteractor {
        return PlaceInteractorImpl(
            placeRepository = placeRepository
        )
    }

    @Provides
    fun provideSeriesInteractor(
        placeRepository: PlaceRepository
    ): SeriesInteractor {
        return SeriesInteractorImpl(
            placeRepository = placeRepository
        )
    }

}