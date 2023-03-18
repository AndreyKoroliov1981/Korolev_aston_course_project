package com.example.rickandmorty.ui.personage.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersUI(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationUI,
    val location: LocationUI,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
):Parcelable

@Parcelize
data class LocationUI(
    val name: String,
    val url: String
):Parcelable