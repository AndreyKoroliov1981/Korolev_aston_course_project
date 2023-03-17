package com.example.rickandmorty.ui.personage.recycler

import com.example.domain.episodes.model.Episode

interface RVOnClickEpisodeListener {
    fun onClicked(item: Episode)
}