package com.vinithius.marvelappchallenge

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
    ): View? {
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
        binding.recyclerViewHeroes.visibility = View.GONE
        binding.loadingListCharacter.visibility = View.VISIBLE
    }

    private fun settingsToolbar() {
        toolbar = (activity as AppCompatActivity?)?.supportActionBar
        toolbar?.setIcon(R.drawable.marvel_logo_small)
        toolbar?.setDisplayShowHomeEnabled(true)
        toolbar?.setDisplayShowTitleEnabled(false)
        toolbar?.hide()
    }

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
        binding.recyclerViewHeroes.layoutManager = linearLayout
        binding.recyclerViewHeroes.setHasFixedSize(true)
        adapter = MarvelAdapter()
        adapter.addLoadStateListener {
            loadState(it)
        }
        adapter.addOnPagesUpdatedListener {
            with(binding) {
                progressBarPaging.isVisible = !progressBarPaging.isVisible
            }
        }
        binding.recyclerViewHeroes.adapter = adapter
    }

    private fun loadState(state: CombinedLoadStates) {
        val visible = state.refresh is LoadState.Loading && adapter.itemCount == 0
        binding.loadingListCharacter.isVisible = visible
        binding.recyclerViewHeroes.isVisible = !visible
        if (!visible) {
            toolbar?.show()
        }
        val error = state.refresh is LoadState.Error
        binding.errorListCharacter.buttonNetworkAgain.isVisible = error
        binding.errorListCharacter.textError.isVisible = error
        binding.errorListCharacter.imageErrorCaptain.isVisible = error
        binding.recyclerViewHeroes.isVisible = !error
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.action_search)
        item?.let { menuItem ->
            searchView = item.actionView as SearchView
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