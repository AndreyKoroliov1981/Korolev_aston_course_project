package com.example.rickandmorty.ui.locations.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationsUi(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
): Parcelable