package com.example.rickandmorty.ui.personage

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
import com.bumptech.glide.Glide
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.FragmentsTags
import com.example.rickandmorty.R
import com.example.rickandmorty.ShowBottomNavBarProvider
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentPersonageBinding
import com.example.rickandmorty.ui.locations.model.LocationsUi
import com.example.rickandmorty.ui.personage.model.CharactersUi
import com.example.rickandmorty.ui.personage.recycler.PersonageAdapter
import com.example.rickandmorty.ui.personage.recycler.RVOnClickEpisodeListener
import com.example.rickandmorty.ui.place.PlaceFragment
import com.example.rickandmorty.ui.series.SeriesFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PersonageFragment : Fragment() {

    private lateinit var binding: FragmentPersonageBinding
    private var personage: CharactersUi? = null
    private var showBottomNavBarProvider: ShowBottomNavBarProvider? = null

    @javax.inject.Inject
    lateinit var vmFactory: PersonageViewModel.PersonageViewModelFactory

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            showBottomNavBarProvider = context as ShowBottomNavBarProvider
        } catch (castException: ClassCastException) {
            // The activity does not implement the ShowBottomNavBar.
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageBinding.inflate(inflater, container, false)
        appComponent.injectPersonageFragment(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBarProvider?.show(false)

        setBackArrowNavigationListener()
        setFields()
        setLocationsListener()
        setPullToRefresh()

        binding.apply {
//            lifecycleScope.launch {  // old version
//          Restartable Lifecycle-aware coroutines https://developer.android.com/topic/libraries/architecture/coroutines
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.stateFlow.collect {
                            binding.pbLoadData.isVisible = it.dataLoading
                            if (it.episodes == emptyList<Episode>()) {
                                rvEpisodes.isVisible = false
                            } else {
                                val episodesRVAdapter = PersonageAdapter(
                                    object : RVOnClickEpisodeListener {
                                        override fun onClicked(item: Episode) {
                                            val itemUi = viewModel.onClickEpisode(item)
                                            parentFragmentManager.beginTransaction()
                                                .replace(
                                                    R.id.fragment_container_view,
                                                    SeriesFragment.newInstance(itemUi),
                                                    FragmentsTags.Series.tag
                                                )
                                                .addToBackStack(null)
                                                .commit()
                                        }
                                    }, it.episodes
                                )
                                rvEpisodes.adapter = episodesRVAdapter
                                rvEpisodes.isVisible = true
                            }
                        }
                    }

                    launch {
                        viewModel.sideEffect.collectLatest { sideEffect ->
                            if (sideEffect is IsErrorData) {
                                writeError(view, sideEffect.errorMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setLocationsListener() {
        binding.tvLocation.setOnClickListener {
            gotoLocationsScreen(viewModel.onClickLocations())
        }

        binding.tvOrigin.setOnClickListener {
            gotoLocationsScreen(viewModel.onClickOrigins())
        }
    }

    private fun gotoLocationsScreen(itemUi: LocationsUi?) {
        if (itemUi != null) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    PlaceFragment.newInstance(itemUi),
                    FragmentsTags.Place.tag
                )
                .addToBackStack(null)
                .commit()
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

        fun newInstance(param1: CharactersUi): PersonageFragment =
            PersonageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Args.PARAM1, param1)
                }
            }
    }

}