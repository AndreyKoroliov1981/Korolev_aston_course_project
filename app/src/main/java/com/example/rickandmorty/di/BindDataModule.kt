package com.example.rickandmorty.di

import com.example.data.repository.characters.CharactersRepositoryImpl
import com.example.data.repository.episodes.EpisodesRepositoryImpl
import com.example.data.repository.locations.LocationsRepositoryImpl
import com.example.data.repository.personage.PersonageRepositoryImpl
import com.example.data.repository.place.PlaceRepositoryImpl
import com.example.domain.characters.CharactersRepository
import com.example.domain.episodes.EpisodesRepository
import com.example.domain.locations.LocationsRepository
import com.example.domain.personage.PersonageRepository
import com.example.domain.place.PlaceRepository
import dagger.Binds
import dagger.Module

@Module
interface  BindDataModule {

    @Binds
    fun bindCharactersRepositoryImpl(charactersRepositoryImpl: CharactersRepositoryImpl) : CharactersRepository

    @Binds
    fun bindPersonageRepositoryImpl(personageRepositoryImpl: PersonageRepositoryImpl) : PersonageRepository

    @Binds
    fun bindEpisodesRepositoryImpl(episodesRepositoryImpl: EpisodesRepositoryImpl) : EpisodesRepository

    @Binds
    fun bindLocationsRepositoryImpl(locationsRepositoryImpl: LocationsRepositoryImpl) : LocationsRepository

    @Binds
    fun bindPlaceRepositoryImpl(placeRepositoryImpl: PlaceRepositoryImpl) : PlaceRepository
}