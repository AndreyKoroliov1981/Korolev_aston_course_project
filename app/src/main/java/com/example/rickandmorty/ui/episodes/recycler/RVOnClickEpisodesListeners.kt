package com.example.rickandmorty.ui.episodes.recycler

import com.example.domain.episodes.model.Episode

interface RVOnClickEpisodesListeners {
    fun onClicked(item: Episode)
}