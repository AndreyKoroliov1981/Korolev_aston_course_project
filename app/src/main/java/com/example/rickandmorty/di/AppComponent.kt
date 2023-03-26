package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.ui.characters.CharactersFragment
import com.example.rickandmorty.ui.episodes.EpisodesFragment
import com.example.rickandmorty.ui.locations.LocationsFragment
import com.example.rickandmorty.ui.personage.PersonageFragment
import com.example.rickandmorty.ui.place.PlaceFragment
import com.example.rickandmorty.ui.series.SeriesFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [RoomDataModule::class, RetrofitDataModule::class, BindDataModule::class, BindDomainModule::class])
interface AppComponent {
    fun injectCharactersFragment(charactersFragment: CharactersFragment)
    fun injectPersonageFragment(personageFragment: PersonageFragment)
    fun injectEpisodesFragment(episodesFragment: EpisodesFragment)
    fun injectLocationsFragment(locationsFragment: LocationsFragment)
    fun injectPlaceFragment(placeFragment: PlaceFragment)
    fun injectSeriesFragment(seriesFragment: SeriesFragment)


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}