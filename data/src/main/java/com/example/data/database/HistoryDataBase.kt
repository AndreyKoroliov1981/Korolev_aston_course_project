package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.characters.CharactersDao
import com.example.data.database.characters.CharactersDb
import com.example.data.database.characters.LocationDb
import com.example.data.database.episodes.EpisodesDao
import com.example.data.database.episodes.EpisodesDb
import com.example.data.database.locations.LocationsDao
import com.example.data.database.locations.LocationsDb

@Database(entities = [CharactersDb::class, LocationsDb::class, EpisodesDb::class], version = 1)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun getHistoryDao(): CharactersDao
    abstract fun getLocationsDao(): LocationsDao
    abstract fun getEpisodesDao(): EpisodesDao

    companion object {
        private var database: HistoryDataBase? = null

        @Synchronized
        fun getInstance(context: Context): HistoryDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, HistoryDataBase::class.java, "db").build()
                database as HistoryDataBase
            } else {
                database as HistoryDataBase
            }
        }
    }
}