package com.vinithius.marvelappchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vinithius.datasource.response.Character
import com.vinithius.marvelappchallenge.databinding.FragmentMarvelDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MarvelDetailFragment : Fragment() {

    private val viewModel by sharedViewModel<MarvelViewModel>()
    private lateinit var binding: FragmentMarvelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetIdCharacter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarvelDetailBinding.inflate(inflater)
//        binding.errorDetailCharacter.buttonNetworkAgain.setOnClickListener {
//            viewModel.getCharactersDetail()
//        }
        setTitles()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerCharacter()
        observerLoading()
        observerError()
        with(viewModel) {
            getCharactersDetail()
        }
    }

    private fun setMainCardDetail(character: Character) {
        binding.textTitle.text = character.name
        binding.textDescription.text = character.description
        with(character.thumbnail) {
            val image = "$path${IMAGE_SIZE}$extension"
            Picasso.get().load(image).into(binding.imageHero)
        }
    }

    private fun observerCharacter() {
        with(viewModel) {
            characterDetail.observe(viewLifecycleOwner) { character ->
                setMainCardDetail(character)
                setEachAdapters(character)
            }
        }
    }

    private fun setEachAdapters(character: Character) {
        with(binding) {
            setGeneralAdapter(
                MarvelGeneralAdapter(character.comicsDetail),
                recyclerViewGeneralComics
            )
            setGeneralAdapter(
                MarvelGeneralAdapter(character.storiesDetail),
                recyclerViewGeneralStories
            )
            setGeneralAdapter(
                MarvelGeneralAdapter(character.seriesDetail),
                recyclerViewGeneralSeries
            )
            setGeneralAdapter(
                MarvelGeneralAdapter(character.eventsDetail),
                recyclerViewGeneralEvents
            )
        }
    }

    private fun setTitles() {
        with(binding) {
            textComics.text = resources.getText(R.string.comics)
            textStories.text = resources.getText(R.string.stories)
            textSeries.text = resources.getText(R.string.series)
            textEvents.text = resources.getText(R.string.events)
        }
    }

    private fun setGeneralAdapter(
        adapter: MarvelGeneralAdapter,
        recyclerView: RecyclerView
    ) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun observerLoading() {
        with(viewModel) {
            characterDetailLoading.observe(viewLifecycleOwner) { loading ->
//                binding.loadingDetailCharacter.isVisible = loading
            }
        }
    }

    private fun observerError() {
        with(viewModel) {
            characterDetailError.observe(viewLifecycleOwner) { error ->
//                binding.errorDetailCharacter.isVisible = error
            }
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_medium."
    }

}