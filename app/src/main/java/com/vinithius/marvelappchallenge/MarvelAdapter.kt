package com.vinithius.marvelappchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinithius.datasource.response.Hero
import com.vinithius.marvelappchallenge.databinding.ViewHolderMarvelBinding

class MarvelAdapter(private val dataSet: List<Hero>) :
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
        fun bind(hero: Hero) {
            binding.textViewHero.text = hero.name
        }
    }
}