package com.example.data.network.characters.model

data class CharactersResponse(
    val info : InfoResponse,
    val results: List<PersonResponse>
)

data class InfoResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)