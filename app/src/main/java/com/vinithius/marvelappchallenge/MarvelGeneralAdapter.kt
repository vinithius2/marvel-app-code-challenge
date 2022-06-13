package com.vinithius.marvelappchallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vinithius.datasource.response.GeneralDetailsCharacter
import com.vinithius.marvelappchallenge.databinding.ViewHolderGeneralBinding

class MarvelGeneralAdapter(private val dataSet: List<GeneralDetailsCharacter>) :
    RecyclerView.Adapter<MarvelGeneralAdapter.MarvelGeneralViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelGeneralViewHolder {
        val binding = ViewHolderGeneralBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarvelGeneralViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarvelGeneralViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class MarvelGeneralViewHolder(private val binding: ViewHolderGeneralBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(general: GeneralDetailsCharacter) {
            binding.textTitle.text = general.title
//            binding.textDescription.text = general.description
            with(general.thumbnail) {
                if (this != null) {
                    val image = "${this?.path}${IMAGE_SIZE}${this?.extension}"
                    Picasso.get().load(image).into(binding.imageGeneral)
                } else {
                    binding.imageGeneral.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_medium."
    }
}