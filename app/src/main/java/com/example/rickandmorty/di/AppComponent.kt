package com.example.rickandmorty.di

import com.example.rickandmorty.ui.characters.CharactersFragment
import dagger.Component

@Component(modules = [AppModule::class,DomainModule::class,DataModule::class])
interface AppComponent {
    fun injectCharactersFragment(charactersFragment: CharactersFragment)
}