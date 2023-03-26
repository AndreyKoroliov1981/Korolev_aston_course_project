package com.example.rickandmorty.di

import android.content.Context
import com.example.data.BuildConfig
import com.example.data.database.HistoryManager
import com.example.data.network.characters.CharactersRetrofitService
import com.example.data.network.episodes.EpisodesRetrofitService
import com.example.data.network.locations.LocationsRetrofitService
import com.example.data.network.personage.PersonageRetrofitService
import com.example.data.network.place.PlaceRetrofitService
import com.example.data.repository.cache.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideCharactersRetrofitService(@Named("Retrofit") retrofit: Retrofit): CharactersRetrofitService {
        return retrofit.create(CharactersRetrofitService::class.java)
    }

    @Provides
    fun provideEpisodesRetrofitService(@Named("Retrofit") retrofit: Retrofit): EpisodesRetrofitService {
        return retrofit.create(EpisodesRetrofitService::class.java)
    }

    @Provides
    fun provideLocationsRetrofitService(@Named("Retrofit") retrofit: Retrofit): LocationsRetrofitService {
        return retrofit.create(LocationsRetrofitService::class.java)
    }

    @Provides
    fun providePersonageRetrofitService(@Named("RetrofitRxJava") retrofit: Retrofit): PersonageRetrofitService {
        return retrofit.create(PersonageRetrofitService::class.java)
    }

    @Provides
    fun providePlaceRetrofitService(@Named("RetrofitRxJava") retrofit: Retrofit): PlaceRetrofitService {
        return retrofit.create(PlaceRetrofitService::class.java)
    }

    @Provides
    @Named("Retrofit")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("RetrofitRxJava")
    fun provideRetrofitRxJava(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideHistoryRepository(context: Context): CharactersHistoryRepository {
        return HistoryManager(context = context)
    }

    @Provides
    fun provideHistoryRepositoryLocations(context: Context): LocationsHistoryRepository {
        return HistoryManager(context = context)
    }

    @Provides
    fun provideHistoryRepositoryEpisodes(context: Context): EpisodesHistoryRepository {
        return HistoryManager(context = context)
    }
}