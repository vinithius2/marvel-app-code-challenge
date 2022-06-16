package com.vinithius.marvelappchallenge.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.vinithius.marvelappchallenge.R
import com.vinithius.marvelappchallenge.databinding.LayoutLoadingBinding

class ComponentCharacterLoading(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private val binding =
        LayoutLoadingBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val loadingAlpha: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.loading_alpha
        )
        val loadingScale: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.loading_scale
        )
        binding.logo.startAnimation(loadingAlpha)
        binding.textLoading.startAnimation(loadingScale)
    }

}