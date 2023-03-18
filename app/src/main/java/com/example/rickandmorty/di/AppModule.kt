package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.domain.personage.PersonageInteractor
import com.example.rickandmorty.ui.characters.CharactersViewModelFactory
import com.example.rickandmorty.ui.personage.model.CharactersUIMapper
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideCharactersUIMapper(): CharactersUIMapper {
        return CharactersUIMapper()
    }

    @Provides
    fun provideCharactersViewModelFactory(
        charactersInteractor: CharactersInteractor,
        charactersUIMapper: CharactersUIMapper,
    ): CharactersViewModelFactory {
        return CharactersViewModelFactory(
            charactersInteractor = charactersInteractor,
            charactersUIMapper = charactersUIMapper
        )
    }
}