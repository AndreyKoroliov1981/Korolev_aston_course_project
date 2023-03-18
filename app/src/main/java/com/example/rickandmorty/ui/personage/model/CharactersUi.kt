package com.example.rickandmorty.ui.personage.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersUi(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationUi,
    val location: LocationUi,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
):Parcelable

@Parcelize
data class LocationUi(
    val name: String,
    val url: String
):Parcelable