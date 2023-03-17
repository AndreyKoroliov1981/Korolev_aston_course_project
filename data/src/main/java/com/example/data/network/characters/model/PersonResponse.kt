package com.example.data.network.characters.model

data class PersonResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationResponse,
    val location: LocationResponse,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationResponse(
    val name: String,
    val url: String
)
