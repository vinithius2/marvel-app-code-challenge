package com.vinithius.marvelappchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinithius.marvelappchallenge.databinding.FragmentMarvelListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MarvelListFragment : Fragment() {

    private val viewModel by sharedViewModel<MarvelViewModel>()
    private lateinit var binding: FragmentMarvelListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarvelListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHeroes.layoutManager = linearLayout
        binding.recyclerViewHeroes.setHasFixedSize(true)
        val adapter = MarvelAdapter()
        binding.recyclerViewHeroes.adapter = adapter

        lifecycleScope.launch {
            viewModel.getCharacter()
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

}