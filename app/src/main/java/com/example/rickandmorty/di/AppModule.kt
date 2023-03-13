package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.rickandmorty.ui.characters.CharactersViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideCharactersViewModelFactory(charactersInteractor: CharactersInteractor): CharactersViewModelFactory {
        return CharactersViewModelFactory(charactersInteractor = charactersInteractor)
    }
}