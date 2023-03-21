package com.example.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "saved_characters")
@TypeConverters(EpisodesConverter::class)
data class CharactersDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded(prefix = "origin")
    val origin: LocationDb,
    @Embedded(prefix = "location")
    val location: LocationDb,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationDb(
    val name: String,
    val url: String
)