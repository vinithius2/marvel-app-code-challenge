package com.vinithius.marvelappchallenge

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import com.vinithius.datasource.response.Character
import com.vinithius.marvelappchallenge.databinding.BottomsheetDescriptionBinding
import com.vinithius.marvelappchallenge.databinding.FragmentMarvelDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MarvelDetailFragment : Fragment() {

    private val viewModel by sharedViewModel<MarvelViewModel>()
    private lateinit var binding: FragmentMarvelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarvelDetailBinding.inflate(inflater)
        binding.errorDetailCharacter.buttonNetworkAgain.setOnClickListener {
            viewModel.getCharactersDetail()
        }
        binding.scrollDetailCharacter.fullScroll(ScrollView.FOCUS_UP)
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
            Picasso.get().load(image)
                .into(binding.imageHero, object : com.squareup.picasso.Callback {

                    override fun onSuccess() {
                        setBackgroundColor()
                    }

                    override fun onError(e: Exception?) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }

    private fun setBackgroundColor() {
        try {
            val bitmap = getBitmapFromView(binding.imageHero)
            bitmap?.let {
                Palette.from(it).generate().run {
                    val light = this.lightVibrantSwatch ?: this.lightMutedSwatch
                    light?.let { color ->
                        binding.mainContainer.setBackgroundColor(color.rgb)
                    }
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e("setBackgroundColor", e.toString())
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun observerCharacter() {
        with(viewModel) {
            characterDetail.observe(viewLifecycleOwner) { character ->
                character?.let {
                    setTitles(it)
                    setVisibilityAdapters(it)
                    setMainCardDetail(it)
                    setEachAdapters(it)
                }
            }
        }
    }

    private fun setVisibilityAdapters(character: Character) {
        with(binding) {
            recyclerViewGeneralComics.isGone = character.comicsDetail.isNullOrEmpty()
            cardViewGeneralComics.isGone = character.comicsDetail.isNullOrEmpty()
            recyclerViewGeneralSeries.isGone = character.seriesDetail.isNullOrEmpty()
            cardViewGeneralSeries.isGone = character.seriesDetail.isNullOrEmpty()
            recyclerViewGeneralEvents.isGone = character.eventsDetail.isNullOrEmpty()
            cardViewGeneralEvents.isGone = character.eventsDetail.isNullOrEmpty()
            recyclerViewGeneralStories.isGone = character.storiesDetail.isNullOrEmpty()
            cardViewGeneralStories.isGone = character.storiesDetail.isNullOrEmpty()
        }
    }

    private fun setEachAdapters(character: Character) {
        with(binding) {
            setGeneralAdapter(
                MarvelGeneralAdapter(character.comicsDetail),
                recyclerViewGeneralComics
            )
            setGeneralAdapter(
                MarvelGeneralAdapter(character.seriesDetail),
                recyclerViewGeneralSeries
            )
            setGeneralAdapter(
                MarvelGeneralAdapter(character.eventsDetail),
                recyclerViewGeneralEvents
            )

            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerViewGeneralStories.layoutManager = layoutManager
            recyclerViewGeneralStories.addItemDecoration(
                DividerItemDecoration(
                    recyclerViewGeneralStories.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            recyclerViewGeneralStories.adapter = MarvelStoriesAdapter(character.storiesDetail)
        }
    }

    private fun setTitles(character: Character) {
        with(binding) {
            val comicsText =
                "${resources.getText(R.string.comics)} (${character.comicsDetail.size})"
            textComics.text = comicsText
            val storiesText =
                "${resources.getText(R.string.stories)} (${character.storiesDetail.size})"
            textStories.text = storiesText
            val seriesText =
                "${resources.getText(R.string.series)} (${character.seriesDetail.size})"
            textSeries.text = seriesText
            val eventsText =
                "${resources.getText(R.string.events)} (${character.eventsDetail.size})"
            textEvents.text = eventsText
        }
    }

    private fun setGeneralAdapter(
        adapter: MarvelGeneralAdapter,
        recyclerView: RecyclerView
    ) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter.apply {
            onCallBackClickDescription = { title, description ->
                bottomSheetDescription(title, description)
            }
        }
    }

    private fun observerLoading() {
        with(viewModel) {
            characterDetailLoading.observe(viewLifecycleOwner) { loading ->
                binding.loadingDetailCharacter.isVisible = loading
            }
        }
    }

    private fun observerError() {
        with(viewModel) {
            characterDetailError.observe(viewLifecycleOwner) { error ->
                binding.errorDetailCharacter.imageErrorCaptain.isGone = !error
                binding.errorDetailCharacter.textError.isGone = !error
                binding.errorDetailCharacter.buttonNetworkAgain.isGone = !error
                binding.scrollDetailCharacter.isGone = error
            }
        }
    }

    private fun bottomSheetDescription(title: String, description: String) {
        context?.let {
            val dialog = BottomSheetDialog(it)
            val bsBinding =
                BottomsheetDescriptionBinding.inflate(LayoutInflater.from(requireContext()))
            bsBinding.bottomsheetTitle.text = title
            bsBinding.bottomsheetDescription.text = description
            bsBinding.buttomClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.setContentView(bsBinding.root)
            dialog.show()
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_uncanny."
    }

}