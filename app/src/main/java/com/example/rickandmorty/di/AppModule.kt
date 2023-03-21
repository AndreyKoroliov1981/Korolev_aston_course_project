package com.example.rickandmorty.di

import android.content.Context
import com.example.domain.characters.CharactersInteractor
import com.example.domain.episodes.EpisodesInteractor
import com.example.domain.locations.LocationsInteractor
import com.example.rickandmorty.ui.characters.CharactersViewModelFactory
import com.example.rickandmorty.ui.episodes.EpisodesViewModelFactory
import com.example.rickandmorty.ui.episodes.model.EpisodesUiMapper
import com.example.rickandmorty.ui.locations.LocationsViewModelFactory
import com.example.rickandmorty.ui.locations.model.LocationsUiMapper
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideCharactersUiMapper(): CharactersUiMapper {
        return CharactersUiMapper()
    }

    @Provides
    fun provideCharactersViewModelFactory(
        charactersInteractor: CharactersInteractor,
        charactersUiMapper: CharactersUiMapper,
    ): CharactersViewModelFactory {
        return CharactersViewModelFactory(
            charactersInteractor = charactersInteractor,
            charactersUiMapper = charactersUiMapper
        )
    }

    @Provides
    fun provideEpisodesUiMapper(): EpisodesUiMapper {
        return EpisodesUiMapper()
    }

    @Provides
    fun provideEpisodesViewModelFactory(
        episodesInteractor: EpisodesInteractor,
        episodesUiMapper: EpisodesUiMapper,
    ): EpisodesViewModelFactory {
        return EpisodesViewModelFactory(
            episodesInteractor = episodesInteractor,
            episodesUiMapper = episodesUiMapper
        )
    }

    @Provides
    fun provideLocationsUiMapper(): LocationsUiMapper {
        return LocationsUiMapper()
    }

    @Provides
    fun provideLocationsViewModelFactory(
        locationsInteractor: LocationsInteractor,
        locationsUiMapper: LocationsUiMapper,
    ): LocationsViewModelFactory {
        return LocationsViewModelFactory(
            locationsInteractor = locationsInteractor,
            locationsUiMapper = locationsUiMapper
        )
    }
}