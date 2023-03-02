package com.example.rickandmorty.di

import com.example.data.network.characters.CharactersCommon
import com.example.data.network.characters.CharactersRetrofitService
import com.example.data.repository.characters.CharactersMapper
import com.example.data.repository.characters.CharactersRepositoryImpl
import com.example.domain.characters.CharactersRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideCharactersRepository(
        charactersMapper: CharactersMapper,
        charactersRetrofitService: CharactersRetrofitService
    ): CharactersRepository {
        return CharactersRepositoryImpl(
            charactersMapper = charactersMapper,
            charactersRetrofitService = charactersRetrofitService
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
}