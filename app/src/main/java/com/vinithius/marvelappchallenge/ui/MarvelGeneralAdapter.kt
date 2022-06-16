package com.vinithius.marvelappchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vinithius.marvelappchallenge.datasource.response.GeneralDetailsCharacter
import com.vinithius.marvelappchallenge.databinding.ViewHolderGeneralBinding


class MarvelGeneralAdapter(private val dataSet: List<GeneralDetailsCharacter>) :
    RecyclerView.Adapter<MarvelGeneralAdapter.MarvelGeneralViewHolder>() {

    var onCallBackClickDescription: ((title: String, description: String) -> Unit)? = null

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
            if (!general.description.isNullOrEmpty()) {
                binding.cardViewMain.setOnClickListener {
                    onCallBackClickDescription?.invoke(general.title, general.description)
                }
            } else {
                binding.icoDescription.isGone = true
            }
            with(general.thumbnail) {
                if (this != null) {
                    val image = "${this.path}$IMAGE_SIZE${this.extension}"
                    Picasso.get().load(image).into(binding.imageGeneral)
                } else {
                    binding.imageGeneral.isGone = true
                }
            }
        }
    }

    companion object {
        const val IMAGE_SIZE = "/portrait_fantastic."
    }
}
