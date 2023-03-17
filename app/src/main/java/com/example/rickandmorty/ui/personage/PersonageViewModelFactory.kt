package com.example.rickandmorty.ui.personage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.personage.PersonageInteractor

class PersonageViewModelFactory(
    val personageInteractor: PersonageInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonageViewModel(
            personageInteractor = personageInteractor,
        ) as T
    }
}