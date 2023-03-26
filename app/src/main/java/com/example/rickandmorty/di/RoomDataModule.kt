package com.example.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.HistoryDataBase
import com.example.data.database.characters.CharactersDao
import com.example.data.database.episodes.EpisodesDao
import com.example.data.database.locations.LocationsDao
import dagger.Module
import dagger.Provides

@Module
class RoomDataModule {

    @Provides
    fun provideCharactersDao(historyDataBase: HistoryDataBase): CharactersDao {
        return historyDataBase.charactersDao()
    }

    @Provides
    fun provideLocationsDao(historyDataBase: HistoryDataBase): LocationsDao {
        return historyDataBase.locationsDao()
    }

    @Provides
    fun provideEpisodesDao(historyDataBase: HistoryDataBase): EpisodesDao {
        return historyDataBase.episodesDao()
    }

    @Provides
    fun provideDataBase(context: Context): HistoryDataBase {
        return Room.databaseBuilder(context, HistoryDataBase::class.java, "db").build()
    }

}