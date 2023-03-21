package com.example.data.database.locations

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.database.StringConverter

@Entity(tableName = "saved_locations")
@TypeConverters(StringConverter::class)
data class LocationsDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)