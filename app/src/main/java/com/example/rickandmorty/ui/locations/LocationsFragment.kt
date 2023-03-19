package com.example.rickandmorty.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.ShowBottomNavBar
import com.example.rickandmorty.databinding.FragmentLocationsBinding

class LocationsFragment : Fragment() {
    private lateinit var binding: FragmentLocationsBinding
    private var showBottomNavBar : ShowBottomNavBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                (activity as? MainActivity)?.changeBottomSelectedIDForMain()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
        try {
            showBottomNavBar = context as ShowBottomNavBar
        } catch (castException: ClassCastException) {
            // The activity does not implement the ShowBottomNavBar.
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBar?.show(true)
    }
}