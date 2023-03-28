package com.example.rickandmorty.app

import android.app.Application
import com.example.rickandmorty.di.AppComponent
import com.example.rickandmorty.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(context = applicationContext)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}