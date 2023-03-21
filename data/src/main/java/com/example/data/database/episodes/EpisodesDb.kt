package com.example.data.database.episodes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.database.StringConverter

@Entity(tableName = "saved_episodes")
@TypeConverters(StringConverter::class)
data class EpisodesDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)