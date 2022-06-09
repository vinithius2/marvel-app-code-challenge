package com.vinithius.marvelappchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vinithius.datasource.response.Character
import com.vinithius.marvelappchallenge.databinding.ViewHolderMarvelBinding

class MarvelAdapter(private val dataSet: List<Character>) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        val binding = ViewHolderMarvelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarvelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class MarvelViewHolder(private val binding: ViewHolderMarvelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.textViewHero.text = character.name
            with(character.thumbnail) {
                val image = "$path$IMAGE_SIZE$extension"
                Picasso.get().load(image).into(binding.imageHero)
            }
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_medium."
    }
}