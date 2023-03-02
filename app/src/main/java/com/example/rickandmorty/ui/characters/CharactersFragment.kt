package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.characters.model.Characters
import com.example.rickandmorty.app.App
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.characters.recycler.CharactersAdapter
import com.example.rickandmorty.ui.characters.recycler.RVOnClickCharactersListeners
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment: Fragment() {
    private lateinit var binding: FragmentCharactersBinding

    @javax.inject.Inject
    lateinit var vmFactory: CharactersViewModelFactory
    private lateinit var viewModel: CharactersViewModel

    private var charactersRVAdapter = CharactersAdapter(
        object : RVOnClickCharactersListeners {
            override fun onClicked(item: Characters) {
                //viewModel.onClickDeleteRequest(item)
                Log.d("my_tag", "clicked characters = ${item.name}")
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.applicationContext as App).appComponent.injectCharactersFragment(this)
        viewModel = ViewModelProvider(this, vmFactory).get(CharactersViewModel::class.java)
        binding.rvCharacters.adapter = charactersRVAdapter
        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.stateFlow.collect {
                            if (it.characters == emptyList<Characters>()) {
                                rvCharacters.isVisible = false
                                //tvNoDataInDB.isVisible = true
                            } else {
                                charactersRVAdapter.updateList(it.characters)
                                rvCharacters.isVisible = true
                                //tvNoDataInDB.isVisible = false
                            }
                        }
                    }

                    launch {
                        viewModel.sideEffect.collectLatest {
//                            if (it is IsNotHomeDataNetwork) {
//                                writeError(view, it.errorMessage)
//                            }
//                            if (it is IsHomeDataNetwork) {
//                                val action =
//                                    HomeFragmentDirections.actionFragmentHomeToFragmentDetail(it.data)
//                                Navigation.findNavController(viewBinding.root).navigate(action)
//                            }
                        }
                    }
                }
            }
        }

    }
}