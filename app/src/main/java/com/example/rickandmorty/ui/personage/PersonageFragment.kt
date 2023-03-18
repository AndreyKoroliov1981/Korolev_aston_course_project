package com.example.rickandmorty.ui.personage

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentPersonageBinding
import com.example.rickandmorty.ui.personage.model.CharactersUI
import com.example.rickandmorty.ui.personage.recycler.PersonageAdapter
import com.example.rickandmorty.ui.personage.recycler.RVOnClickEpisodeListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PersonageFragment : Fragment() {
    private lateinit var binding: FragmentPersonageBinding
    private var personage: CharactersUI? = null

    @javax.inject.Inject
    lateinit var vmFactory: PersonageViewModel.PersonageViewModelFactory

//    private lateinit var viewModel: PersonageViewModel

    private val viewModel: PersonageViewModel by viewModels {
        PersonageViewModel.providesFactory(
            assistedFactory = vmFactory,
            personage = arguments?.getParcelable(Args.PARAM1)!!
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            personage = args.getParcelable(Args.PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.injectPersonageFragment(this)
        (activity as? MainActivity)?.showBottomNavBar(false)

        setBackArrowNavigationListener()
        setFields()
        setPullToRefresh()

        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        viewModel.stateFlow.collect {
                            binding.pbLoadData.isVisible = it.dataLoading
                            if (it.episodes == emptyList<Episode>()) {
                                rvEpisodes.isVisible = false
                            } else {
                                Log.d("my_tag", "it.episodes = ${it.episodes}")
                                val episodesRVAdapter = PersonageAdapter(
                                    object : RVOnClickEpisodeListener {
                                        override fun onClicked(item: Episode) {
                                            viewModel.onClickEpisode(item)
                                        }
                                    }, it.episodes
                                )
                                rvEpisodes.adapter = episodesRVAdapter
                                rvEpisodes.isVisible = true
                            }
                        }
                    }

                    launch {
                        viewModel.sideEffect.collectLatest {
                            if (it is IsErrorData) {
                                writeError(view, it.errorMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setFields() {
        with(binding) {
            if (personage != null) {
                Glide.with(requireContext())
                    .load(personage!!.image)
                    .centerCrop()
                    .into(ivImage)

                tvName.text = personage!!.name
                tvStatus.text = personage!!.status
                tvSpecies.text = personage!!.species
                tvGender.text = personage!!.gender
                tvLocation.text = personage!!.location.name
                tvOrigin.text = personage!!.origin.name
            }
        }
    }

    private fun setBackArrowNavigationListener() {
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setPullToRefresh() {
        binding.srRefresh.setOnRefreshListener {
            binding.srRefresh.isRefreshing = true
            setFields()
            personage?.episode?.let { viewModel.getEpisodes(it) }
            binding.srRefresh.isRefreshing = false
        }
    }

    private fun writeError(view: View, error: String) {
        val snackBarView =
            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.error_text))
                .setAction("Retry") {
                    viewModel.onClickSendRequest()
                }
        val sbView = snackBarView.view
        val params = sbView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        sbView.layoutParams = params
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    companion object {
        private object Args {
            const val PARAM1 = "param1"
        }

        fun newInstance(param1: CharactersUI): PersonageFragment =
            PersonageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Args.PARAM1, param1)
                }
            }
    }

}