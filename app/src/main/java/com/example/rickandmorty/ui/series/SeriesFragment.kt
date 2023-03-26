package com.example.rickandmorty.ui.series

import android.content.Context
import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.characters.model.Characters
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.FragmentsTags
import com.example.rickandmorty.R
import com.example.rickandmorty.ShowBottomNavBar
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentSeriesBinding
import com.example.rickandmorty.ui.characters.recycler.CharactersAdapter
import com.example.rickandmorty.ui.characters.recycler.RVOnClickCharactersListeners
import com.example.rickandmorty.ui.episodes.model.EpisodesUi
import com.example.rickandmorty.ui.personage.PersonageFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesFragment: Fragment() {
    private lateinit var binding: FragmentSeriesBinding
    private var series: EpisodesUi? = null
    private var showBottomNavBar: ShowBottomNavBar? = null

    @javax.inject.Inject
    lateinit var vmFactory: SeriesViewModel.PlaceViewModelFactory

    private val viewModel: SeriesViewModel by viewModels {
        SeriesViewModel.providesFactory(
            assistedFactory = vmFactory,
            series = arguments?.getParcelable(Args.PARAM1)!!
        )
    }

    private var charactersRVAdapter = CharactersAdapter(
        object : RVOnClickCharactersListeners {
            override fun onClicked(item: Characters) {
                val itemUI = viewModel.mapCharactersToCharactersUI(item)

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container_view,
                        PersonageFragment.newInstance(itemUI),
                        FragmentsTags.Personage.tag
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            series = args.getParcelable(Args.PARAM1)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        appComponent.injectSeriesFragment(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBar?.show(false)
        binding.rvCharacter.adapter = charactersRVAdapter
        setBackArrowNavigationListener()
        setFields()
        setPullToRefresh()

        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        viewModel.stateFlow.collect {
                            binding.pbLoadData.isVisible = it.dataLoading
                            if (it.residents == emptyList<Episode>()) {
                                rvCharacter.isVisible = false
                            } else {
                                charactersRVAdapter.updateList(it.residents)
                                rvCharacter.isVisible = true
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
            if (series != null) {
                tvName.text = series!!.name
                tvAirDate.text = series!!.airDate
                tvEpisode.text = series!!.episode
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
            series?.characters?.let { viewModel.refreshLoad(it) }
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

        fun newInstance(param1: EpisodesUi): SeriesFragment =
            SeriesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Args.PARAM1, param1)
                }
            }
    }

}