package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.domain.episodes.EpisodesInteractor
import com.example.rickandmorty.ui.characters.CharactersViewModelFactory
import com.example.rickandmorty.ui.episodes.EpisodesViewModelFactory
import com.example.rickandmorty.ui.episodes.model.EpisodesUiMapper
import com.example.rickandmorty.ui.personage.model.CharactersUiMapper
import dagger.Module
import dagger.Provides

@Module
class AppModule {

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
}