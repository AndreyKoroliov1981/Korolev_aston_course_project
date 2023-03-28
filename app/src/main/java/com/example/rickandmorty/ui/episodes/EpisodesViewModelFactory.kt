package com.example.rickandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.episodes.EpisodesInteractor
import com.example.rickandmorty.ui.episodes.model.EpisodesUiMapper
import javax.inject.Inject

class EpisodesViewModelFactory
@Inject constructor(
    private val episodesInteractor: EpisodesInteractor,
    private val episodesUiMapper: EpisodesUiMapper,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodesViewModel(
            episodesInteractor = episodesInteractor,
            episodesUiMapper = episodesUiMapper
        ) as T
    }
}