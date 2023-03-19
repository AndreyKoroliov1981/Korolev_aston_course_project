package com.example.rickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.characters.model.Characters
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.FragmentsTags
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.ShowBottomNavBar
import com.example.rickandmorty.app.App
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsEmptyFilter
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.characters.CharactersViewModel
import com.example.rickandmorty.ui.characters.recycler.CharactersAdapter
import com.example.rickandmorty.ui.characters.recycler.RVOnClickCharactersListeners
import com.example.rickandmorty.ui.episodes.recycler.EpisodesAdapter
import com.example.rickandmorty.ui.episodes.recycler.RVOnClickEpisodesListeners
import com.example.rickandmorty.ui.personage.PersonageFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodesFragment: Fragment() {
    private lateinit var binding: FragmentEpisodesBinding
    private var showBottomNavBar : ShowBottomNavBar? = null

    @javax.inject.Inject
    lateinit var vmFactory: EpisodesViewModelFactory
    private lateinit var viewModel: EpisodesViewModel

    private var episodesRVAdapter = EpisodesAdapter(
        object : RVOnClickEpisodesListeners {
            override fun onClicked(item: Episode) {
                val itemUi = viewModel.mapEpisodeToEpisodesUi(item)
                //TODO add transaction
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container_view, PersonageFragment.newInstance(itemUI), FragmentsTags.Personage.tag)
//                    .addToBackStack(null)
//                    .commit()
            }
        }
    )

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
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        appComponent.injectEpisodesFragment(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBar?.show(true)
        viewModel = ViewModelProvider(this, vmFactory).get(EpisodesViewModel::class.java)
        binding.rvEpisodes.adapter = episodesRVAdapter
        setRVScrollListeners()
        setSearchViewQueryTextListeners()
        setPullToRefresh()

        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.stateFlow.collect {
                            if (it.episodes == emptyList<Characters>()) {
                                rvEpisodes.isVisible = false
                            } else {
                                episodesRVAdapter.updateList(it.episodes)
                                rvEpisodes.isVisible = true
                            }

                            if (it.dataLoading) binding.pbLoad.visibility = View.VISIBLE else {
                                binding.pbLoad.visibility = View.INVISIBLE
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

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    private fun setPullToRefresh() {
        binding.srRefresh.setOnRefreshListener {
            binding.srRefresh.isRefreshing = true
            viewModel.refreshLoad()
            binding.srRefresh.isRefreshing = false
        }
    }

    private fun setSearchViewQueryTextListeners() {
        binding.svName.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) viewModel.changeSearchViewQueryName(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) viewModel.changeSearchViewQueryName(newText)
                return false
            }
        })

        binding.svEpisodes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) viewModel.changeSearchViewQueryEpisode(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) viewModel.changeSearchViewQueryEpisode(newText)
                return false
            }
        })
    }

    private fun setRVScrollListeners() {
        val layoutManager = binding.rvEpisodes.layoutManager as StaggeredGridLayoutManager
        binding.rvEpisodes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemArray = layoutManager.findLastVisibleItemPositions(null)
                val lastVisibleItem = getLastVisibleItem(lastVisibleItemArray)
                if (lastVisibleItem > totalItemCount - 10) {
                    viewModel.getEpisodes()
                }
            }
        })
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
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
}