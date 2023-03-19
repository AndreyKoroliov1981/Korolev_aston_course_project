package com.example.rickandmorty.ui.episodes

import com.example.domain.episodes.EpisodesInteractor
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.characters.DEBOUNCE_MILS
import com.example.rickandmorty.ui.episodes.model.EpisodesUi
import com.example.rickandmorty.ui.episodes.model.EpisodesUiMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

class EpisodesViewModel (
    private val episodesInteractor: EpisodesInteractor,
    private val episodesUiMapper: EpisodesUiMapper,
) : BaseViewModel<EpisodesState>(EpisodesState()) {

    private var job: Job? = null

    init {
        refreshLoad()
    }

    fun refreshLoad() {
        updateState { copy(episodes = emptyList()) }
        episodesInteractor.setStartPage()
        getEpisodes()
    }

    fun mapEpisodeToEpisodesUi(item: Episode): EpisodesUi {
        return episodesUiMapper.mapEpisodesFromDomain(item)
    }

    fun getEpisodes() {
        job?.cancel()
        job = launch {
            delay(DEBOUNCE_MILS)
            updateState { copy(dataLoading = true) }
            var isCheckedEndLoadFromApi = true
            while (isCheckedEndLoadFromApi) {
                val responseListEpisodes =
                    episodesInteractor.getEpisodes(
                        searchName = state.searchName,
                        searchEpisodes = state.searchEpisodes
                    )
                if (responseListEpisodes.errorText == null) {
                    val listEpisodes = responseListEpisodes.data ?: emptyList()
                    isCheckedEndLoadFromApi = listEpisodes.isEmpty()
                    val newEpisodes = state.episodes + listEpisodes
                    updateState { copy(episodes = newEpisodes) }
                } else {
                    isCheckedEndLoadFromApi = false
                    sideEffectSharedFlow.emit(IsErrorData(responseListEpisodes.errorText!!))
                }
                updateState { copy(dataLoading = false) }
            }
        }
    }

    fun changeSearchViewQueryName(query: String) {
        if (query != state.searchName) {
            updateState { copy(episodes = emptyList(), searchName = query) }
            getEpisodes()
        }
    }

    fun changeSearchViewQueryEpisode(query: String) {
        if (query != state.searchEpisodes) {
            updateState { copy(episodes = emptyList(), searchEpisodes = query) }
            getEpisodes()
        }
    }

    fun onClickSendRequest() {
        getEpisodes()
    }

}