package com.vinithius.marvelappchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinithius.marvelappchallenge.databinding.FragmentMarvelListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MarvelListFragment : Fragment() {

    private val viewModel by sharedViewModel<MarvelViewModel>()
    private lateinit var binding: FragmentMarvelListBinding
    private lateinit var adapter: MarvelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarvelListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerHeroes()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHeroes()
    }

    private fun observerHeroes() {
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHeroes.layoutManager = linearLayout
        viewModel.heroes.observe(viewLifecycleOwner) { heroes ->
            adapter = MarvelAdapter(heroes)
            binding.recyclerViewHeroes.adapter = adapter
        }
    }

}