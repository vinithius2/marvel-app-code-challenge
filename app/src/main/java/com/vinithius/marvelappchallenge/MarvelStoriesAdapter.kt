package com.vinithius.marvelappchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinithius.datasource.response.GeneralDetailsCharacter
import com.vinithius.marvelappchallenge.databinding.ViewHolderStoriesBinding

class MarvelStoriesAdapter(private val dataSet: List<GeneralDetailsCharacter>) :
    RecyclerView.Adapter<MarvelStoriesAdapter.StoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = ViewHolderStoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class StoriesViewHolder(private val binding: ViewHolderStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(general: GeneralDetailsCharacter) {
            binding.textTitle.text = general.title
        }
    }
}
