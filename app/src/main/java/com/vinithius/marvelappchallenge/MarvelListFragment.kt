package com.vinithius.marvelappchallenge

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinithius.marvelappchallenge.databinding.FragmentMarvelListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MarvelListFragment : Fragment() {

    private val viewModel by sharedViewModel<MarvelViewModel>()
    private lateinit var binding: FragmentMarvelListBinding
    private lateinit var adapter: MarvelAdapter
    private lateinit var searchView: SearchView
    private var toolbar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentMarvelListBinding.inflate(inflater)
        binding.errorListCharacter.buttonNetworkAgain.setOnClickListener {
            searchCharacters()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsToolbar()
        setAdapter()
        searchCharacters()
        binding.recyclerViewHeroes.isGone = true
        binding.loadingListCharacter.isGone = false
    }

    private fun settingsToolbar() {
        toolbar = (activity as AppCompatActivity?)?.supportActionBar
        toolbar?.let {
            it.setIcon(R.drawable.marvel_logo_small)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    /**
     * Function to make the call with or without a filter to search for characters.
     */
    private fun searchCharacters(nameStartsWith: String? = null) {
        with(viewModel) {
            getCharactersList(nameStartsWith)?.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setAdapter() {
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        with(binding) {
            recyclerViewHeroes.layoutManager = linearLayout
            recyclerViewHeroes.setHasFixedSize(true)
            adapter = MarvelAdapter()
            adapter.addLoadStateListener {
                loadStateErro(it)
                loadStateLoading(it)
            }
            adapter.addOnPagesUpdatedListener {
                progressBarPaging.isVisible = !progressBarPaging.isVisible
            }
            recyclerViewHeroes.adapter = adapter.apply {
                onCallBackClickDetail = { id ->
                    viewModel.setIdCharacter(id)
                    findNavController().navigate(
                        R.id.action_listMarvelFragment_to_detailMarvelFragment,
                    )
                }
            }
        }
    }

    /**
     * Function to pass the 'error' state to the respective view, in this function it is possible to
     * enable the component's visibility.
     */
    private fun loadStateErro(state: CombinedLoadStates) {
        with(binding) {
            val error = state.refresh is LoadState.Error
            errorListCharacter.buttonNetworkAgain.isVisible = error
            errorListCharacter.textError.isVisible = error
            errorListCharacter.imageErrorCaptain.isVisible = error
            recyclerViewHeroes.isVisible = !error
            if (!error && !loadingListCharacter.isVisible) {
                textListEmpty.isVisible = adapter.itemCount == 0
            }
        }
    }

    /**
     * Function to pass the 'loading' state to the respective view, in this function it is possible
     * to enable the component's visibility.
     */
    private fun loadStateLoading(state: CombinedLoadStates) {
        with(binding) {
            val visible = state.refresh is LoadState.Loading && adapter.itemCount == 0
            loadingListCharacter.isVisible = visible
            recyclerViewHeroes.isVisible = !visible
        }
    }

    /**
     * Create a search in toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.action_search)
        item?.let { menuItem ->
            searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(nameStartsWith: String?): Boolean {
                    nameStartsWith?.let { searchCharacters(it) }
                    return false
                }

                override fun onQueryTextChange(nameStartsWith: String?): Boolean {
                    nameStartsWith?.let {
                        if (nameStartsWith?.isEmpty()) {
                            searchCharacters()
                        }
                    }
                    return false
                }
            })
            menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                    searchCharacters()
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu, menuInflater)
    }
}
