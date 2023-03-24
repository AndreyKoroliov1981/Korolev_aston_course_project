package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.CharactersInteractorImpl
import com.example.domain.episodes.EpisodesInteractor
import com.example.domain.episodes.EpisodesInteractorImpl
import com.example.domain.locations.LocationsInteractor
import com.example.domain.locations.LocationsInteractorImpl
import com.example.domain.personage.PersonageInteractor
import com.example.domain.personage.PersonageInteractorImpl
import com.example.domain.place.PlaceInteractor
import com.example.domain.place.PlaceInteractorImpl
import com.example.domain.series.SeriesInteractor
import com.example.domain.series.SeriesInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface BindDomainModule {

    @Binds
    fun bindCharactersInteractorImpl(charactersInteractorImpl: CharactersInteractorImpl): CharactersInteractor

    @Binds
    fun bindPersonageInteractorImpl(personageInteractorImpl: PersonageInteractorImpl): PersonageInteractor

    @Binds
    fun bindEpisodesInteractorImpl(episodesInteractorImpl: EpisodesInteractorImpl): EpisodesInteractor

    @Binds
    fun bindLocationsInteractorImpl(locationsInteractorImpl: LocationsInteractorImpl): LocationsInteractor

    @Binds
    fun bindPlaceInteractorImpl(placeInteractorImpl: PlaceInteractorImpl): PlaceInteractor

    @Binds
    fun bindSeriesInteractorImpl(SeriesInteractorImpl: SeriesInteractorImpl): SeriesInteractor
}