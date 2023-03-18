package com.example.rickandmorty.di

import com.example.rickandmorty.ui.characters.CharactersFragment
import com.example.rickandmorty.ui.episodes.EpisodesFragment
import com.example.rickandmorty.ui.personage.PersonageFragment
import dagger.Component

@Component(modules = [AppModule::class,DomainModule::class,DataModule::class])
interface AppComponent {
    fun injectCharactersFragment(charactersFragment: CharactersFragment)
    fun injectPersonageFragment(personageFragment: PersonageFragment)
    fun injectEpisodesFragment(episodesFragment: EpisodesFragment)
}