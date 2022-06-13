package com.vinithius.marvelappchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vinithius.datasource.response.Character
import com.vinithius.marvelappchallenge.databinding.ViewHolderMarvelBinding

class MarvelAdapter :
    PagingDataAdapter<Character, MarvelAdapter.MarvelViewHolder>(COMPARATOR) {

    var onCallBackClickDetail: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val binding = ViewHolderMarvelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarvelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MarvelViewHolder(private val binding: ViewHolderMarvelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                cardViewHolder.setOnClickListener {
                    onCallBackClickDetail?.invoke(character.id)
                }
                textViewHero.text = character.name
                shapeSeries.textTitle.text = root.resources.getString(R.string.series)
                shapeSeries.textNumber.text = character.series.available.toString()
                shapeEvents.textTitle.text = root.resources.getString(R.string.events)
                shapeEvents.textNumber.text = character.events.available.toString()
                shapeStories.textTitle.text = root.resources.getString(R.string.stories)
                shapeStories.textNumber.text = character.stories.available.toString()
                with(character.thumbnail) {
                    val image = "$path$IMAGE_SIZE$extension"
                    Picasso.get().load(image).into(imageHero)
                }
            }
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_medium."

        private object COMPARATOR : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}