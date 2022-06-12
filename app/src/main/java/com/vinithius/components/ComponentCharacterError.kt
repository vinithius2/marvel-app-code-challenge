package com.vinithius.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.vinithius.marvelappchallenge.R
import com.vinithius.marvelappchallenge.databinding.IncludeLayoutErrorBinding

class ComponentCharacterError(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    var onCallBackClickRefresh: (() -> Unit)? = null

    private val binding =
        IncludeLayoutErrorBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val loadingScale: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.captain_marvel_error
        )
        binding.imageErrorCaptain.startAnimation(loadingScale)
        onCallBackClickRefresh?.invoke()
    }

//    fun setOnClick(click: (() -> Unit)) {
//        binding.buttonRefresh.setOnClickListener {
//            click.invoke()
//        }
//    }

}
