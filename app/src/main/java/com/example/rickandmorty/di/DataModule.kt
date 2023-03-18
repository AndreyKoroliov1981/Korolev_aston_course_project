package com.example.rickandmorty.di

import com.example.data.network.characters.CharactersCommon
import com.example.data.network.characters.CharactersRetrofitService
import com.example.data.network.personage.PersonageCommon
import com.example.data.network.personage.PersonageRetrofitService
import com.example.data.repository.characters.CharactersMapper
import com.example.data.repository.characters.CharactersRepositoryImpl
import com.example.data.repository.personage.EpisodeMapper
import com.example.data.repository.personage.PersonageRepositoryImpl
import com.example.domain.characters.CharactersRepository
import com.example.domain.personage.PersonageRepository
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

    @Provides
    fun providePersonageRepository(
        episodeMapper: EpisodeMapper,
        personageRetrofitService: PersonageRetrofitService
    ): PersonageRepository {
        return PersonageRepositoryImpl(
            episodeMapper = episodeMapper,
            personageRetrofitService = personageRetrofitService
        )
    }

    @Provides
    fun provideEpisodeMapper(): EpisodeMapper {
        return EpisodeMapper()
    }

    @Provides
    fun providePersonageRetrofitService(): PersonageRetrofitService {
        return PersonageCommon.personageRetrofitService
    }
}