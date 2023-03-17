package com.example.rickandmorty.di

import com.example.domain.characters.CharactersInteractor
import com.example.domain.characters.CharactersInteractorImpl
import com.example.domain.characters.CharactersRepository
import com.example.domain.personage.PersonageInteractor
import com.example.domain.personage.PersonageInteractorImpl
import com.example.domain.personage.PersonageRepository
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

}