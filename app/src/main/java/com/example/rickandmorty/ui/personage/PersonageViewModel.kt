package com.example.rickandmorty.ui.personage

import android.util.Log
import com.example.domain.episodes.model.Episode
import com.example.domain.personage.PersonageInteractor
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData

class PersonageViewModel(
    private val personageInteractor: PersonageInteractor,
) : BaseViewModel<PersonageState>(PersonageState()) {

    fun getEpisodes(episodes: List<String>) {
        Log.d("my_tag","getEpisodes start")
        launch {
            updateState { copy(dataLoading = true) }
            val responseListEpisode = personageInteractor.getEpisodes(episodes)
            if (responseListEpisode.errorText == null) {
                val listEpisodes = responseListEpisode.data ?: emptyList()
                Log.d("my_tag","listEpisodes = $listEpisodes")
                updateState { copy(episodes = listEpisodes) }
            } else {
                sideEffectSharedFlow.emit(IsErrorData(responseListEpisode.errorText!!))
            }
            updateState { copy(dataLoading = false) }
        }
    }

    fun onClickEpisode(item: Episode) {
        Log.d("my_tag","item = ${item.episode}")
    }

    fun onClickSendRequest() {
        Log.d("my_tag","PersonageViewModel onClickSendRequest")
    }

}