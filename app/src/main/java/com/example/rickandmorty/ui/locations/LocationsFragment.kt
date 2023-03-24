package com.example.rickandmorty.ui.locations

import android.content.Context
import android.os.Bundle
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
import com.example.domain.locations.model.Locations
import com.example.rickandmorty.FragmentsTags
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.ShowBottomNavBarProvider
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.ui.locations.recycler.LocationsAdapter
import com.example.rickandmorty.ui.locations.recycler.RVOnClickLocationsListeners
import com.example.rickandmorty.ui.place.PlaceFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationsFragment : Fragment() {
    private lateinit var binding: FragmentLocationsBinding
    private var showBottomNavBarProvider : ShowBottomNavBarProvider? = null

    @javax.inject.Inject
    lateinit var vmFactory: LocationsViewModelFactory
    private lateinit var viewModel: LocationsViewModel

    private var locationsRVAdapter = LocationsAdapter(
        object : RVOnClickLocationsListeners {
            override fun onClicked(item: Locations) {
                val itemUi = viewModel.mapLocationToLocationsUi(item)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, PlaceFragment.newInstance(itemUi), FragmentsTags.Place.tag)
                    .addToBackStack(null)
                    .commit()
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
            showBottomNavBarProvider = context as ShowBottomNavBarProvider
        } catch (castException: ClassCastException) {
            // The activity does not implement the ShowBottomNavBar.
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        appComponent.injectLocationsFragment(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavBarProvider?.show(true)
        viewModel = ViewModelProvider(this, vmFactory).get(LocationsViewModel::class.java)
        binding.rvLocations.adapter = locationsRVAdapter
        setRVScrollListeners()
        setSearchViewQueryTextListeners()
        setPullToRefresh()

        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.stateFlow.collect {
                            if (it.locations == emptyList<Characters>()) {
                                rvLocations.isVisible = false
                            } else {
                                locationsRVAdapter.updateList(it.locations)
                                rvLocations.isVisible = true
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

        binding.svType.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        val layoutManager = binding.rvLocations.layoutManager as StaggeredGridLayoutManager
        binding.rvLocations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemArray = layoutManager.findLastVisibleItemPositions(null)
                val lastVisibleItem = getLastVisibleItem(lastVisibleItemArray)
                if (lastVisibleItem > totalItemCount - 10) {
                    viewModel.getLocations()
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