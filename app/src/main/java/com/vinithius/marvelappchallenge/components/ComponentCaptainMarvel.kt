package com.vinithius.marvelappchallenge.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.vinithius.marvelappchallenge.R
import com.vinithius.marvelappchallenge.databinding.CaptainMarvelBinding

class ComponentCaptainMarvel(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val binding =
        CaptainMarvelBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        val loadingScale: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.captain_marvel_error
        )
        binding.imageErrorCaptain.startAnimation(loadingScale)
    }

}
