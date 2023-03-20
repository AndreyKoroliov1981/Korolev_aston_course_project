package com.example.rickandmorty.ui.personage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.episodes.model.Episode
import com.example.domain.personage.PersonageInteractor
import com.example.rickandmorty.common.BaseViewModel
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.ui.characters.DEBOUNCE_MILS
import com.example.rickandmorty.ui.personage.model.CharactersUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import javax.inject.Inject

class PersonageViewModel @AssistedInject constructor(
    private val personageInteractor: PersonageInteractor,
    @Assisted private val personage: CharactersUi
) : BaseViewModel<PersonageState>(PersonageState()) {

    @AssistedFactory
    interface PersonageViewModelFactory {
        fun create(recordId: CharactersUi): PersonageViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: PersonageViewModelFactory,
            personage: CharactersUi
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(personage) as T
            }
        }
    }

    init {
        getEpisodes(personage!!.episode)
    }

    fun getEpisodes(episodes: List<String>) {
        launch {
            delay(DEBOUNCE_MILS)
            updateState { copy(dataLoading = true) }
            val responseListEpisode = personageInteractor.getEpisodes(episodes)
            if (responseListEpisode.errorText == null) {
                val listEpisodes = responseListEpisode.data ?: emptyList()
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

    //TODO При нажатии на локацию (location) или же место происхождения (origin) необходимо открывать детали выбранной локации.

    fun onClickSendRequest() {
        Log.d("my_tag","PersonageViewModel onClickSendRequest")
    }

}