package com.example.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.characters.CharactersFragment
import com.example.rickandmorty.ui.episodes.EpisodesFragment
import com.example.rickandmorty.ui.locations.LocationsFragment

class MainActivity : AppCompatActivity(), ShowBottomNavBarProvider {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomOnItemSelectedListener()
    }

    private fun setBottomOnItemSelectedListener() {
        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_characters -> {
                    replaceFragment(CharactersFragment(), FragmentsTags.Characters.tag)
                }
                R.id.action_locations -> {
                    replaceFragment(LocationsFragment(), FragmentsTags.Locations.tag)
                }
                R.id.action_episodes -> {
                    replaceFragment(EpisodesFragment(), FragmentsTags.Episodes.tag)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment, tag)
            .commit()
    }

    fun changeBottomSelectedIDForMain() {
        binding.bottomNavBar.selectedItemId = R.id.action_characters
    }

    override fun show(show: Boolean) {
        binding.bottomNavBar.isVisible = show
    }
}