package com.example.rickandmorty.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
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
import com.example.rickandmorty.FragmentsTags
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.R
import com.example.rickandmorty.app.App.Companion.appComponent
import com.example.rickandmorty.common.IsEmptyFilter
import com.example.rickandmorty.common.IsErrorData
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.characters.recycler.CharactersAdapter
import com.example.rickandmorty.ui.characters.recycler.RVOnClickCharactersListeners
import com.example.rickandmorty.ui.personage.PersonageFragment
import com.example.rickandmorty.ui.personage.model.CharactersUIMapper
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding

    @javax.inject.Inject
    lateinit var vmFactory: CharactersViewModelFactory
    private lateinit var viewModel: CharactersViewModel

    private var charactersRVAdapter = CharactersAdapter(
        object : RVOnClickCharactersListeners {
            override fun onClicked(item: Characters) {
                val itemUI = viewModel.mapCharactersToCharactersUI(item)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, PersonageFragment.newInstance(itemUI), FragmentsTags.Personage.tag)
                    .addToBackStack(null)
                    .commit()
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

    override fun onResume() {
        super.onResume()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.injectCharactersFragment(this)
        viewModel = ViewModelProvider(this, vmFactory).get(CharactersViewModel::class.java)
        (activity as? MainActivity)?.showBottomNavBar(true)
        binding.rvCharacters.adapter = charactersRVAdapter
        setRVScrollListeners()
        setChipFiltersListeners()
        setSearchViewQueryTextListeners()

        binding.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.stateFlow.collect {
                            if (it.characters == emptyList<Characters>()) {
                                rvCharacters.isVisible = false
                            } else {
                                charactersRVAdapter.updateList(it.characters)
                                rvCharacters.isVisible = true
                            }

                            if (it.dataLoading) binding.pbLoad.visibility = View.VISIBLE else {
                                binding.pbLoad.visibility = View.INVISIBLE
                            }

                            setStateChipFilter(
                                binding.cAlive,
                                it.chipFilterAliveInstalled
                            )
                            setStateChipFilter(
                                binding.cDead,
                                it.chipFilterDeadInstalled
                            )
                            setStateChipFilter(
                                binding.cUnknown,
                                it.chipFilterUnknownInstalled
                            )

                        }
                    }

                    launch {
                        viewModel.sideEffect.collectLatest {
                            if (it is IsErrorData) {
                                writeError(view, it.errorMessage)
                            }

                            if (it is IsEmptyFilter) {
                                writeError(view, resources.getString(R.string.text_error_empty_filter))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setSearchViewQueryTextListeners() {
        binding.svCharacters.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("my_tag","start onQueryTextSubmit")
                if (query != null) viewModel.changeSearchViewQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("my_tag","start onQueryTextChange")
                if (newText != null) viewModel.changeSearchViewQuery(newText)
                return false
            }
        })
    }

    private fun setRVScrollListeners() {
        val layoutManager = binding.rvCharacters.layoutManager as StaggeredGridLayoutManager
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemArray = layoutManager.findLastVisibleItemPositions(null)
                val lastVisibleItem = getLastVisibleItem(lastVisibleItemArray)
                if (lastVisibleItem > totalItemCount - 10) {
                    viewModel.getCharacters()
                }
            }
        })
    }

    private fun setStateChipFilter(view: Chip, flag: Boolean) {
        view.isChecked = flag
    }

    private fun setChipFiltersListeners() {
        binding.cAlive.setOnClickListener {
            viewModel.clickChipFilterAlive()
        }
        binding.cDead.setOnClickListener {
            viewModel.clickChipFilterDead()
        }
        binding.cUnknown.setOnClickListener {
            viewModel.clickChipFilterUnknown()
        }
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